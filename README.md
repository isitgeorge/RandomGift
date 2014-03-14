RandomGift
==========

###Description

RandomGift randomly rewards a lucky online player with a gift when another
player logs into the game. Using this plugin encourages people to play and spend
time on the server regularly throughout the day as they are rewarded for doing
so.

RandomGift is fully configurable, including the item pool, minimum players
required to use it and amount of time between each gift chance.

***

###Permissions and Commands

<table style="width:100%">
    <tr>
        <td><b>Description</b></td>
        <td><b>Command</b></td>
        <td><b>Permission</b></td>
    </tr>
    <tr>
        <td>Allows player to trigger the RandomGift </td>
        <td>N/A</td>
        <td><code>randomgift.trigger</code></td>
    </tr>
    <tr>
        <td>Allows player to receive the RandomGift</td>
        <td>N/A</td>
        <td><code>randomgift.receive</code></td>
    </tr>
    <tr>
        <td>View amount of time remaining before RandomGift is active again</td>
        <td><code>/randomgift cooldown</code></td>
        <td><code>randomgift.cooldown</code></td>
    </tr>
    <tr>
        <td>Reset the remaining cooldown time</td>
        <td><code>/randomgift cooldown reset</code></td>
        <td><code>randomgift.cooldown.reset</code></td>
    </tr>
    <tr>
        <td>Manually trigger RandomGift (&#60;player&#62 is trigger)</td>
        <td><code>/randomgift gift &#60;player&#62</code></td>
        <td><code>randomgift.gift</code></td>
    </tr>
    <tr>
        <td>Reload the configuration file</td>
        <td><code>/randomgift reload</code></td>
        <td><code>randomgift.reload</code></td>
    </tr>
<tr>
        <td>Access to everything above</td>
        <td>N/A</td>
        <td><code>randomgift.*</code></td>
    </tr>
</table>

***

###Suggested Permission Configuration

-   **Staff**
 -   `randomgift.trigger`
 -   `randomgift.cooldown`
 -   `randomgift.cooldown.reset`
 -   `randomgift.gift`
 -   `randomgift.reload`
-   **Players**
 -   `randomgift.trigger`
 -   `randomgift.recieve`

`randomgift.*` is also available to allow access to everything, recommended for Administrators.

***

###Default item pool
-   1x 267 (Iron Sword)
-   1x 256 (Iron Shovel)
-   1x 257 (Iron Pickaxe)
-   1x 258 (Iron Axe)
-   1x 306 (Iron Helmet)
-   1x 307 (Iron Chestplate)
-   1x 308 (Iron Leggings)
-   1x 309 (Iron Boots)
-   1x 264 (Diamond)

***

###Configuration

The configuration file can be found inside /plugins/RandomGift once the server
has been started with RandomGift installed.


<pre>
# RandomGift configuration
# Detailed information at http://dev.bukkit.org/bukkit-plugins/randomgift/

# Items that are chosen at random
#  - 'ID[:data value] Quantity'
items:
  - '267 1'
  - '256 1'
  - '257 1'
  - '258 1'
  - '306 1'
  - '307 1'
  - '308 1'
  - '309 1'
  - '264 1'
  
# Minimum amount of players required to be online to trigger a gift
minimum-players: 3

# Include players without the 'receive' permission in the minimum player count
all-players: true

# Amount of minutes before RandomGift can be used again
cooldown-time: 5

# Broadcasts a message to the entire server when a player receives a gift
broadcast-message: true 

# Checks for an update on startup and displays a message if there is a more recent version available
version-check: true

# Statistic data
# Plugin Metrics is used to collect anonymous statistic data about the plugins usage and sends it to http://mcstats.org/plugin/randomgift
# This plugin also sends its current version number back to the developer for statistical purposes. 
# This information is simply used for tracking current versions in use, and the data collected will only be seen by the developer(s).
collect-statistics: true
</pre>

***

###Future Features
If you have a suggestion for a future feature, drop it into the comments section.

***

###Development Builds
Development builds of this project can be acquired at the provided continuous integration server.
These builds have not been approved by the BukkitDev staff. Use them at your own risk.

[Last Successful Build](http://ci.isitgeo.com/browse/RG-RAN/latestSuccessful/artifact)

Last build: ![Build Status](http://ci.isitgeo.com/plugins/servlet/buildStatusImage/RG-RAN)
***

##Contributers
View the [contributers](https://github.com/isitgeorge/RandomGift/graphs/contributors) that help keep RandomGift awesome!

###Notes
**Update check**

The version of the plugin is automatically checked against the latest available
when you start up the server to see if an update is available, this
functionality can be disabled in the configuration file.

**Data collection**

RandomGift uses Plugin Metrics that collects anonymous statistic data about the
plugins usage and sends it to [http://mcstats.org/plugin/randomgift](http://mcstats.org/plugin/randomgift)

RandomGift also sends its current version number back to its developer for
statistical purposes. This information is simply used for tracking current
versions in use, and the data collected will only be seen by the developer(s).

![MCStats](http://api.mcstats.org/signature/RandomGift.png)

*If you prefer, you can disable both the Plugin Metrics and sending of version
data inside the configuration file as seen above. You can also disable Plugin
Metrics globally inside its own configuration located at /plugins/Plugin
Metrics/config.yml*

[Source is available on GitHub](http://github.com/isitgeorge/randomgift) Pull requests and suggestions are welcome!
