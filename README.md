[1]: https://jlospinoso.github.io/groovy/gorm/java/spring%20boot/mogreet/software/2015/09/14/dailyc-batch-mms-service.html

# dailyc - A Batch Multimedia Message Service
dailyc is a service for sending multimedia messages at regular intervals. You provide dailyc with a folder full of `jpg`s and a list of messages; it will determine which image and message hasn't been sent in a while (if ever) and fire it off to a list of subscribers.

The service works really well for sending baby pictures to adoring (petulant?) relatives. It is well suited to this task because, well, that's why I implemented it.

See my [blog post][1] for configuration instructions.