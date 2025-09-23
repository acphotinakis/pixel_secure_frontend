# import os

# # Define base directories
# base_dirs = [
#     "src/main/java/com/videogamedb/cli",
#     "src/main/java/com/videogamedb/cli/commands",
#     "src/main/java/com/videogamedb/cli/services",
#     "src/main/java/com/videogamedb/cli/models",
#     "src/main/java/com/videogamedb/cli/util",
#     "src/main/resources",
#     "src/test/java/com/videogamedb/cli/services",
# ]

# # Define files to create: (relative_path, optional initial content)
# files = [
#     (".env", ""),
#     ("README.md", "# Video Game CLI Application\n\n"),
#     ("src/main/resources/application.properties", ""),
#     (
#         "src/main/java/com/videogamedb/cli/Main.java",
#         'package com.videogamedb.cli;\n\npublic class Main {\n    public static void main(String[] args) {\n        System.out.println("Video Game CLI started.");\n    }\n}\n',
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/commands/AuthCommand.java",
#         "package com.videogamedb.cli.commands;\n\npublic class AuthCommand {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/commands/UserCommand.java",
#         "package com.videogamedb.cli.commands;\n\npublic class UserCommand {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/commands/GameCommand.java",
#         "package com.videogamedb.cli.commands;\n\npublic class GameCommand {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/commands/CollectionCommand.java",
#         "package com.videogamedb.cli.commands;\n\npublic class CollectionCommand {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/services/AuthService.java",
#         "package com.videogamedb.cli.services;\n\npublic class AuthService {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/services/UserService.java",
#         "package com.videogamedb.cli.services;\n\npublic class UserService {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/services/GameService.java",
#         "package com.videogamedb.cli.services;\n\npublic class GameService {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/services/ApiClient.java",
#         "package com.videogamedb.cli.services;\n\npublic class ApiClient {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/models/LoginRequest.java",
#         "package com.videogamedb.cli.models;\n\npublic class LoginRequest {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/models/SignUpRequest.java",
#         "package com.videogamedb.cli.models;\n\npublic class SignUpRequest {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/models/JwtResponse.java",
#         "package com.videogamedb.cli.models;\n\npublic class JwtResponse {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/models/User.java",
#         "package com.videogamedb.cli.models;\n\npublic class User {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/util/ConfigLoader.java",
#         "package com.videogamedb.cli.util;\n\npublic class ConfigLoader {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/util/ConsoleUtils.java",
#         "package com.videogamedb.cli.util;\n\npublic class ConsoleUtils {}\n",
#     ),
#     (
#         "src/main/java/com/videogamedb/cli/util/SecurityUtils.java",
#         "package com.videogamedb.cli.util;\n\npublic class SecurityUtils {}\n",
#     ),
#     (
#         "src/test/java/com/videogamedb/cli/services/AuthServiceTest.java",
#         "package com.videogamedb.cli.services;\n\npublic class AuthServiceTest {}\n",
#     ),
# ]

# # Create directories
# for d in base_dirs:
#     os.makedirs(d, exist_ok=True)

# # Create files
# for file_path, content in files:
#     with open(file_path, "w", encoding="utf-8") as f:
#         f.write(content)

# print("✅ Project structure generated successfully!")

import os

# Base directories to create
base_dirs = [
    "src/main/java/com/videogamedb/cli/models",
    "src/main/java/com/videogamedb/cli/services",
    "src/main/java/com/videogamedb/cli/commands",
    "src/main/java/com/videogamedb/cli/utils",
]

# Java files for each module
model_files = [
    "Contributor.java",
    "OwnedGame.java",
    "VideoGame.java",
    "PlaySession.java",
    "Collection.java",
    "Rating.java",
    "Platform.java",
    "Follow.java",
    "AccessTime.java",
    "Genre.java",
    "PlatformRelease.java",
]

service_files = [
    "ContributorService.java",
    "OwnedGameService.java",
    "VideoGameService.java",
    "PlaySessionService.java",
    "CollectionService.java",
    "RatingService.java",
    "PlatformService.java",
    "FollowService.java",
    "AccessTimeService.java",
    "GenreService.java",
    "PlatformReleaseService.java",
]

command_files = [
    "ContributorCommand.java",
    "OwnedGameCommand.java",
    "VideoGameCommand.java",
    "PlaySessionCommand.java",
    "CollectionCommand.java",
    "RatingCommand.java",
    "PlatformCommand.java",
    "FollowCommand.java",
    "AccessTimeCommand.java",
    "GenreCommand.java",
    "PlatformReleaseCommand.java",
]

# Create directories
for d in base_dirs:
    os.makedirs(d, exist_ok=True)


# Helper function to create Java files with package declaration
def create_java_file(dir_path, filename):
    package_name = dir_path.replace("src/main/java/", "").replace("/", ".")
    file_path = os.path.join(dir_path, filename)
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(
            f"package {package_name};\n\npublic class {filename.replace('.java','')} {{\n}}\n"
        )


# Create model files
for file in model_files:
    create_java_file("src/main/java/com/videogamedb/cli/models", file)

# Create service files
for file in service_files:
    create_java_file("src/main/java/com/videogamedb/cli/services", file)

# Create command files
for file in command_files:
    create_java_file("src/main/java/com/videogamedb/cli/commands", file)

print("✅ Video Game CLI project structure generated successfully!")
