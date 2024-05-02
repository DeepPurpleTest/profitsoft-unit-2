# Running the Project in Docker
1. Use docker-compose --env-file .env up -d in terminal of project root

# Running the Application for Development Locally
1. In the docker-compose.yml file, comment out the block "backend".
2. Run the following command to start services in Docker: docker-compose --env-file .env up -d

## swagger link: http://localhost:8080/swagger-ui/index.html# 

## File for upload at endpoint /api/projects/upload in resources with name projects-data.json
