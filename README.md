Running the Project in Docker
1. Use docker-compose --env-file .env up -d in terminal of project root

Running the Application for Development Locally
1. In the .env file, comment out the block mark as prod-env, and uncomment block mark as dev-env.
2. In the docker-compose.yml file, comment out the block "backend".
3. Open your terminal and navigate to the project's root directory.
4. Run the following command to start services in Docker: docker-compose --env-file .env up -d
5. Copy dev-env from .env file
6. Edit Configuration -> click Edit environment variables -> Paste
