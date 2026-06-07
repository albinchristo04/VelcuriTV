# Velcuri TV Deployment Guide

## 1. Server Setup
- Ubuntu 24.04 LTS VPS (minimum 2 vCPU, 4GB RAM)
- Install aaPanel: `bash <(curl -o- https://raw.githubusercontent.com/aaPanel/aapanel/master/install.sh)`
- Install via aaPanel: Nginx, PHP 8.3, MySQL 8, Redis, Let's Encrypt

## 2. DNS Records (all pointing to VPS IP)
```
api.velcuri.io       → A record → <VPS IP>
admin.velcuri.io     → A record → <VPS IP>
repo.velcuri.io      → A record → <VPS IP>
download.velcuri.io  → A record → <VPS IP>
```

## 3. Database Setup
```sql
CREATE DATABASE velcuri_tv CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'velcuri_user'@'localhost' IDENTIFIED BY 'STRONG_PASSWORD';
GRANT ALL PRIVILEGES ON velcuri_tv.* TO 'velcuri_user'@'localhost';
FLUSH PRIVILEGES;
```

## 4. Laravel API Deployment
```bash
cd /www/wwwroot/api.velcuri.io
git clone https://github.com/YOUR_FORK/velcuri-api.git .
composer install --no-dev --optimize-autoloader
cp .env.example .env
php artisan key:generate
# Edit .env with your DB credentials, Redis password, etc.
php artisan migrate --force
php artisan db:seed --class=AdminSeeder
php artisan config:cache
php artisan route:cache
php artisan view:cache
chown -R www:www /www/wwwroot/api.velcuri.io
```

## 5. Nginx — api.velcuri.io
```nginx
server {
    listen 443 ssl;
    server_name api.velcuri.io;
    root /www/wwwroot/api.velcuri.io/public;
    index index.php;

    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;

    location / {
        try_files $uri $uri/ /index.php?$query_string;
    }

    location ~ \.php$ {
        fastcgi_pass unix:/tmp/php-cgi-83.sock;
        fastcgi_param SCRIPT_FILENAME $realpath_root$fastcgi_script_name;
        include fastcgi_params;
    }
}
```

## 6. Nginx — repo.velcuri.io (static files with CORS)
```nginx
server {
    listen 443 ssl;
    server_name repo.velcuri.io;
    root /www/wwwroot/repo.velcuri.io;

    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;

    add_header Access-Control-Allow-Origin *;
    add_header Access-Control-Allow-Methods "GET, OPTIONS";

    location / {
        try_files $uri $uri/ =404;
    }
}
```

Deploy repo files:
```bash
mkdir -p /www/wwwroot/repo.velcuri.io/{velcuri,cncverse,mega}
# Copy the repositories/ directory contents from this repo:
cp -r repositories/* /www/wwwroot/repo.velcuri.io/
chown -R www:www /www/wwwroot/repo.velcuri.io
```

## 7. Nginx — download.velcuri.io (static APK hosting)
```nginx
server {
    listen 443 ssl;
    server_name download.velcuri.io;
    root /www/wwwroot/download.velcuri.io;

    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;

    location / {
        try_files $uri =404;
    }
}
```

## 8. Queue Worker via Supervisor (aaPanel)
```ini
[program:velcuri-worker]
process_name=%(program_name)s_%(process_num)02d
command=php /www/wwwroot/api.velcuri.io/artisan queue:work --queue=default --sleep=3 --tries=3
autostart=true
autorestart=true
stopasgroup=true
killasgroup=true
user=www
numprocs=2
redirect_stderr=true
stdout_logfile=/www/wwwroot/api.velcuri.io/storage/logs/worker.log
```

## 9. Firewall (UFW)
```bash
ufw allow 80/tcp
ufw allow 443/tcp
ufw allow 22/tcp
ufw enable
```

## 10. SSL Certificates
Use aaPanel's Let's Encrypt integration to issue SSL certs for all four domains.

## 11. First APK Release
1. Build signed APK: `./gradlew assembleRelease`
2. Sign with your keystore
3. Upload to `/www/wwwroot/download.velcuri.io/velcuri-tv-1.0.0.apk`
4. Log into admin panel at `https://admin.velcuri.io/admin`
5. Navigate to App Versions → Create New
6. Set version `1.0.0`, version code `100`, download URL `https://download.velcuri.io/velcuri-tv-1.0.0.apk`
7. Toggle Active = ON

## 12. Admin Panel Access
- URL: `https://admin.velcuri.io/admin`
- Email: `admin@velcuri.io`
- Password: `VelcuriAdmin2025!` (change immediately after first login)

## 13. Cron Job (Laravel Scheduler)
Add to crontab (`crontab -e` as www user):
```
* * * * * php /www/wwwroot/api.velcuri.io/artisan schedule:run >> /dev/null 2>&1
```
