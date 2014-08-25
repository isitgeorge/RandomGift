RandomGift
==========
####Reward your active players!

***

###Description

RandomGift randomly rewards a lucky online player with a gift when a fellow player logs into the server. 
RandomGift encourages players to be active and spend time playing on the server regularly throughout the day as they are rewarded.

RandomGift is fully customisable, including the gift pool, minimum players required to activate RandomGift and amount of cooldown time between each gift reward.

***

###Default item pool

<table style="width:100%; display:table">
    <tr>
        <td>&nbsp;</td>
        <td><b>Name</b></td>
        <td><b>Quantity</b></td>
        <td><b>ID</b></td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/diamondgem_icon32.png"></td>
        <td>Diamond</td>
        <td>1</td>
        <td>264</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironsword_icon32.png"></td>
        <td>Iron Sword</td>
        <td>1</td>
        <td>267</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironshovel_icon32.png"></td>
        <td>Iron Shovel</td>
        <td>1</td>
        <td>256</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironpickaxe_icon32.png"></td>
        <td>Iron Pickaxe</td>
        <td>1</td>
        <td>257</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironaxe_icon32.png"></td>
        <td>Iron Shovel</td>
        <td>1</td>
        <td>258</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironhelmet_icon32.png"></td>
        <td>Iron Helmet</td>
        <td>1</td>
        <td>306</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironchestplate_icon32.png"></td>
        <td>Iron Chestplate</td>
        <td>1</td>
        <td>307</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironleggings_icon32.png"></td>
        <td>Iron Leggings</td>
        <td>1</td>
        <td>308</td>
    </tr>
    <tr>
        <td><img src="http://dl.isitgeorge.com/u/159751046/BUKKIT/randomgift/images/ironboots_icon32.png"></td>
        <td>Iron Boots</td>
        <td>1</td>
        <td>309</td>
    </tr>
</table>

***

###Permissions and Commands

<table style="width:100%; display:table">
    <tr>
        <td><b>Description</b></td>
        <td><b>Command</b></td>
        <td><b>Permission</b></td>
    </tr>
    <tr>
        <td>Allows player to trigger the RandomGift </td>
        <td><code>N/A</code></td>
        <td><code>randomgift.trigger</code></td>
    </tr>
    <tr>
        <td>Allows player to receive the RandomGift</td>
        <td><code>N/A</code></td>
        <td><code>randomgift.receive</code></td>
    </tr>
    <tr>
        <td>View amount of time remaining before RandomGift is active again</td>
        <td><code>/randomgift cooldown</code></td>
        <td><code>randomgift.cooldown</code></td>
    </tr>
    <tr>
        <td>Reset the remaining cooldown time</td>
        <td><code>/randomgift reset</code></td>
        <td><code>randomgift.reset</code></td>
    </tr>
    <tr>
        <td>Manually trigger RandomGift</td>
        <td><code>/randomgift gift &#60;trigger player&#62</code></td>
        <td><code>randomgift.gift</code></td>
    </tr>
    <tr>
        <td>Reload the configuration file</td>
        <td><code>/randomgift reload</code></td>
        <td><code>randomgift.reload</code></td>
    </tr>
    <tr>
        <td>View last player that received a gift</td>
        <td><code>/randomgift history</code></td>
        <td><code>randomgift.history</code></td>
    </tr>
    <tr>
        <td>Notifications for Administrators</td>
        <td><code>N/A</code></td>
        <td><code>randomgift.admin</code></td>
    </tr>
	<tr>
        <td>Access to everything above</td>
        <td><code>N/A</code></td>
        <td><code>randomgift.*</code></td>
    </tr>
</table>

***

###Suggested Permission Setup

-   **Administrators**
 -   `randomgift.admin`
 -   Inherit Staff
 
-   **Staff**
 -   `randomgift.trigger`
 -   `randomgift.cooldown`
 -   `randomgift.reset`
 -   `randomgift.gift`
 -   `randomgift.reload`
 -   `randomgift.history`
 
-   **Players**
 -   `randomgift.trigger`
 -   `randomgift.receive`

`randomgift.*` is also available to allow access to everything, recommended for Owners/Administrators.

***

###Configuration

The configuration file can be found inside /plugins/RandomGift once the server has been started with RandomGift installed.

Old configuration files do not update themselves, you are required to either manually update the config file yourself or generate a new one and copy your previous configuration ([more info](http://bit.ly/RndmGiftOldCfg)). 

You can view detailed configuration information [here](http://bit.ly/RndmGiftConfig).

***

###Future Features
If you have a suggestion for a future feature, drop it into the comments section.

***

###Development Builds
Development builds of this project can be acquired at the provided continuous integration server. 
These builds have not been approved by the BukkitDev staff. Use them at your own risk.

[Last Successful Build](http://ci.isitgeo.com/browse/RAN-PLUG/latestSuccessful/artifact)

***

###Contributers
View the [contributers](https://github.com/isitgeorge/RandomGift/graphs/contributors) that help keep RandomGift awesome!

***

###Notes
**Update check**

RandomGift checks the version of itself against the latest available version on startup, this can be disabeld inside config.yml.

**Data collection**

RandomGift uses Plugin Metrics to collect anonymous statistic data about RandomGifts usage and sends it to [http://mcstats.org/plugin/randomgift](http://mcstats.org/plugin/randomgift).

RandomGift also sends its current version number back to its developer for statistical purposes. 
This information is used for tracking current versions in use, and the data collected will only be seen by the developer(s).

![MCStats](http://api.mcstats.org/signature/RandomGift.png)

*If you prefer, you can disable both the Plugin Metrics and sending of version data inside the configuration file as seen above. 
You can also disable Plugin Metrics globally inside its own configuration located at /plugins/Plugin Metrics/config.yml*

Help RandomGift conquer the world on [GitHub](http://github.com/isitgeorge/randomgift)?
