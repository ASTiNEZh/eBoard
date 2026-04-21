rootProject.name = "eBoard"

include(
    "infra:APIContracts",
    "infra:DBMigrations",
    "services:AdvertsCRUD",
    "services:AdvertViewer",
    "services:CategoryCRUD",
    "services:CommentsCRUD",
    "services:FileManager",
    "services:UsersCRUD",
    "services:UsersManager"
)