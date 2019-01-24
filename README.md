# GitLab Line Notify Manager With Spring boot
Spring (Web,Security,Data), Thymeleaf, MySQL, adminer

## Getting Started
1. Create New application in your GitLab account for OAuth (may be need Administrator permission)
    - Redirect URI : *http://localhost:9090/login*
    - Trusted : *true*
    - Scopes : *read_user*
2. Copy GitLab Application ID	and Secret 
3. Create Line Notify Service at https://notify-bot.line.me/
4. Setup environment in .env file

### Prerequisites
1. Docker
2. Maven

### Installing
```
mvn clean package -DskipTests
docker-compose up -d
```
### Test
1. Generate Service Token at https://notify-bot.line.me/my/ 
2. Go to http://localhost:9090
3. Create new notify with line token
4. Copy notify_url
5. Create GitLab Webhook in Your GitLab Repository > Setting > Integrations > URL : *notify_url*
6. Make event to your gitlab repository

### Setup
![](/diagram/images/1.png)
------------
### Workflow
![](/diagram/images/2.png)