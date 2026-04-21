rootProject.name = "eBoard"

include(
    "infra:APIContracts",
    "infra:DBMigrations",
    "services:UsersCRUD",
    "services:AdvertsCRUD",
    "services:CommentsCRUD",
    "services:UsersManager"
)