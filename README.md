# TDailyGift
<img src="https://img.shields.io/badge/Version-V1.0_Snapshot-62B47A?style=for-the-badge">
<img src="https://img.shields.io/badge/Java-17+-F80000?style=for-the-badge&logo=Oracle&logoColor=white">

### Support Version
Tested in version 1.19.4. No actionable guarantees for other versions.

***

### Command
* **/daily**
  + Opens the inventory where you can receive the daily gifts.
* **/dailyedit**
  + Opens the inventory where you can modify daily gifts entries.
 
***

### Permissions
* **dailygift.open**
  + Permission for the ```/daily``` command. By default, it is granted to all players.
* **dailygift.edit**
  + Permission for the ```/dailyedit``` command. By default, it is only granted to the administrator players.

***

### Configuration
* **config.yml**
  + ```
    # Whether to display a message if a player who can get a daily gift joins the server.
    welcome-message:
      enable: true
      message: '&bYou can get a daily gift now! Enter the "/daily" command and take it!'
      
    # Phrases to be displayed in the game.
    message-lang:
      already-get-today: '&aYou already got the today daily gift.'
      already-get-month: '&aYou already got all the daily gifts for this month.'
      inventory-full: '&cInventory is full. Please empty your inventory and get the gift.'
      get-daily-gift: '&a&lGet Daily Gift'
      date-display-item-separator: '&7==================='
      date-display-item: '&7Day %s can be received.'
      received-display-item: '&7Received.'
      no-daily-gift: '&fNo daily gift.'
    ```
* **Other YAML files are for storing data.**