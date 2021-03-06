![CI](https://github.com/teverett/mailcradle/workflows/CI/badge.svg)


# MailCradle

MailCradle is a server-side system for sorting, deleting, and forwarding mail on an IMAP Server

## Why is it named MailCradle?

There is mail that is important, and mail that can be read and responded to later.  Typically, I like to store the messages that need immediate attention in my INBOX, and automatically move all other mail into folders to process at a convenient time. In short, I want to separate the urgent mail from all other mail, and sort the mail that's not urgent.

The sorting process is similar to [winnowing](https://en.wikipedia.org/wiki/Winnowing), and that tool that has been used for centuries to winnow grain is a *cradle*.  Therefore, I named the program MailCradle.  Another name for a cradle is a [winnowing-fan](https://www.google.com/search?q=winnowing+fan), if you're interested to learn a bit about it.


# Contributing

MailCradle is a work in progress. Issues and Pull Requests are welcome.

# License

MailCradle is distributed under the 3-Clause BSD license.

# Building MailCradle

MailCradle builds with [maven](https://maven.apache.org/index.html) and requires Java11+.

To build MailCradle:

`mvn clean package`

# Running MailCradle

`java -jar target/mailcradle-1.0.0-SNAPSHOT.jar --config=mailcradle.properties`

# Configuration

The mail `mailcradle.properties` contains IMAP login properties, and the name of a mail sorting rules file. You will need to configure the properties file for your SMTP and IMAP server.

Here's an example:

```java
# IMAP settings
imap.host=mail.example.com
imap.username=uuu
imap.password=ppp
imap.inbox=INBOX
imap.crawlfolders=INBOX
imap.crawlintervalhours=24
imap.crawlRateSeconds=10
imap.port=993
imap.tls=true
imap.keepaliveminutes=5

# SMTP settings
smtp.host=mail.example.com
smtp.username=uuu
smtp.password=ppp
smtp.from=me@example.com
smtp.port=465
smtp.tls=true

# rules file
mailsortFile=mailcradle.txt
```

`imap.folders` is a comma-delimited list.  A trailing splat symbol `*` indicates to recurse folders under the parent folder.


# Rules

There are two types of rules that MailCradle can process:

* Simple Rules
* List Rules

The full rule syntax in [ANTLR](https://www.antlr.org/) format is [here](https://github.com/teverett/mailcradle/blob/master/src/main/antlr4/com/khubla/mailcradle/mailcradle.g4).

Rules are run in the order that they are read from rule files, from top to bottom, including included rule files.

## Simple Rules

Simple rules take the format 

```java
if (<maildata> <condition> <value>) {
	<actions>
};
```

The maildata fields can be:

* subject
* body
* from
* to
* cc
* bcc
* header ["fieldname"]

The condition can be:

* contains
* is

and `value` is a string such as "me@here.com".


## List Rules

List rules take the format

```java
if (<listname> contains <value>) {
	<actions>
};
```

List rules require that a named list be defined to use. Lists are defined as:

```java
list <listname> "value1", "value2", "value3", ... ;
```

## Expressions

Rules can be combined with the `and` and `or` operators and rule negation is supported via the `not` operator.

This rule finds all mail that is from me, to me, and moves it to a folder:

```java
if ((me contains from) and (me contains to)) {
	moveto "INBOX.Me";
};

list me

"tom@khubla.com",
"tom@0x544745.com";
```

This rule finds all mail that is not addressed to me

```java
if (!(me contains to)) {
	// do stuff
};

list me

"tom@khubla.com",
"tom@0x544745.com";
```

## Actions

There are numerous actions which can be run

## moveto

Move the message to an IMAP folder

```java
moveto <foldername>
```

**foldername** must be a quoted string such as "me@example.com".  If the target IMAP folder does not exists it is created and subscribed to.

## replywith

Reply to the message

```java
replywith <content>
```

**content** must be a quoted string such as "This mailbox is not monitored"


## forwardto

Forward the message to an email address

```java
forwardto <address>
```

**address** must be a quoted string such as "someone@example.com"

## stop

The `stop` action stops all rule processing for a message.  It can be used to ensure that once a rule is run further rules are not run which would result in scenarios such as moving a message many times.

## flag

Add an IMAP flag to the message.

```java
flag <flagname>
```

**flagname** must be a quoted string such as "flagged"


## unflag

Remove an IMAP flag from the message.

```java
unflag <flagname>
```

**flagname** must be a quoted string such as "flagged".  The standard flags which IMAP servers support are enumerated in [RFC 3501](https://tools.ietf.org/html/rfc3501)


## Importing rule files

Rule files can include other rule files.  Simply use the `import` keyword


```java
import "family.txt";
```

## Comments

MailCradle rule files use the c-style `//` to indicate line comments. For example

```java
// this rules file contains a list of email addresses for family members
import "family.txt";
```

# Example rules

## Example Files

The source tree contains example rule files in the directory [/examples](https://github.com/teverett/mailcradle/tree/master/examples).

## Example Simple Rules

A rule to move all email from "@github.com" to a folder called "INBOX.github"

```java
if (from contains "@github.com") {
	moveto "INBOX.github";
};
```

A rule to move all "Free Money" spam to the trash

```java
if (subject contains "Free Money") {
	moveto "INBOX.Trash";
};
```

An out of office email

```java
if (from contains "@example.com") {
	replywith "I am out of office";
};
```

Move all messages with the `X-Spam-Flag` header to "YES" to a folder

```java
if (header["X-Spam-Flag"] is "YES") {
	moveto "INBOX.Spam";
};
```

## Example List rules

A blackhole for spammers, using the subject field

```java
list badsubjects "$", "Money", "Free";
if (badsubjects contains subject) {
	moveto "INBOX.Trash";
};
```

Mark all emails from a special list of senders with the IMAP "flagged" flag.

```java
list importantpeople "person1@example.com", "person2@example.com", "person3@example.com";
if (importantpeople contains from) {
	flag "flagged";
};
```

Forward all emails from certain domains and then save then in a folder

```java
list domainlist "@domain1.com", "@domain2.com";
if (domainlist contains from) {
	forwardto "address@example.com";
	moveto "INBOX.AutoForwarded";
};
```


