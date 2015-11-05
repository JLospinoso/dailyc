[1]: https://jlospinoso.github.io/groovy/gorm/java/spring%20boot/mogreet/software/2015/09/14/dailyc-batch-mms-service.html
[2]: https://jlospinoso.github.io/groovy/gorm/java/spring%20boot/apache%20commons%20email/software/2015/11/04/dailyc-v2.html
[3]: https://github.com/JLospinoso/dailyc/blob/master/dailyc-core/src/main/resources/application.properties
[4]: http://www.h2database.com/html/main.html
[5]: http://gradle.org/
[6]: https://spring.io/guides/gs/gradle/#_build_your_project_with_gradle_wrapper

# dailyc - A Batch Multimedia Message Service
dailyc is a service for sending multimedia messages and emails at regular 
intervals. You provide dailyc with a folder full of `jpg`s and a list of 
messages; it will determine which image and message hasn't been sent in a while 
(if ever) and fire it off to a list of subscribers.

The service works really well for sending baby pictures to adoring (petulant?) 
relatives. It is well suited to this task because, well, that's why I 
implemented it.

## Setup
You'll need to pull `dailyc` down:

	git clone git@github.com:JLospinoso/dailyc.git

You must edit two files to get `dailyc` up and running. First is 
`application.properties`, which you can find [here][3] in

	dailyc/dailyc-core/src/main/resources/

Set each value as appropriate. As [noted in this blog post], you'll need to 
sign up for both a Mogreet account and have SMTP access to a mail server. 
Optionally, you can configure `dailyc` to persist state into a database. The 
default is to use the [in-memory only database h2][4]

The other file to edit is `config.json`. You can move this file wherever you 
like, so long as `application.properties` points to it correctly, e.g.

	configPath=/home/johndoe/config.json

This file can be changed at runtime without recompiling/restarting/etc. The 
batch process picks this file up off disk just in time for processing.

	{
		"imageDirectory": "../img",
		"subscribers": [
			"5551234567"
		],
		"emailSubscribers": [
			"dailyc@lospi.net"
		],
		"messages": [
			"Hello world!",
			"Foo!"
		]
	}

Finally, you'll need to fill a (flat) directory with `.jpg` files. This 
directory should be pointed to by `config.json` (as above). At runtime, 
`dailyc` will scoop all the images out of this directory, compare their hashes 
with what it's persisisted in the database, and commit any new ones--so you can 
add images as you like without having to restart the service (same with 
`messages`, which you type directly into `config.json`).

## Dependencies
[Gradle][5] manages the entire build process, to include dependencies. You can 
[use the gradle wrapper][6] and start the service:

	gradlew bootRun

That should do it!
	
## Version history

* [v0.2.0][2] Provides for email service in addition to multimedia messages

* [v0.1.0][1] Initial release, provides multimedia message subscription, 
gorm-backed persistence, Spring Batch integration, and a standalone Mogreet API 
wrapper
