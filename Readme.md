[![Build Status](https://travis-ci.org/keepplace/keepplace-backend.svg)](https://travis-ci.org/keepplace/keepplace-backend)
[![Coverage Status](https://coveralls.io/repos/keepplace/keepplace-backend/badge.svg?branch=wip&service=github)](https://coveralls.io/github/keepplace/keepplace-backend?branch=wip)

## Get started
### Installing deps
```bash
brew install youtube-dl ffmpeg libav fakeroot
```
### Creating database

``` bash
bin/sbt video-podcast-downloader-rest/universal:stage
```

```bash
java -jar apps/rest/target/universal/stage/lib/org.liquibase.liquibase-core-*.jar \
  --classpath="$(echo apps/rest/target/universal/stage/lib/*.jar | tr ' ' ':')" \
  --driver=org.h2.Driver \
  --username=video \
  --password=video \
  --changeLogFile=migrations/changelog.xml \
  --url="jdbc:h2:./keep_place_development" \
  "update"
```


## Development
### Running application
```bash
bin/sbt video-podcast-downloader-rest/run
```

## API

### Authentication
#### Login

##### Description
Allows user to login to the application via OAuth workflow. After successful login user is redirected to `redirection URL` configured in application. If login was unsuccessful (user decided not to grant any privileges to application) then user is redirected to login page with `401 Unauthorized response code`  

Open in browser URL:
```
http://localhost:8080/auth/login
```

#### Profile

##### Description
Returns information about currently logged in user in JSON format.
Returns `401 HTTP Code` if user is not logged in.  

##### Example of request
```bash
curl -XGET --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/auth/profile
```

##### Example of response
```json
{
  id: 123,
  username: "Ritchard White",
  dropboxId: "1234567",
  rssToken: "d481e7c2-669c-41f1-b8e4-a6c00502b6c1"
}

```

### RSS channel
##### Description
Service which returns a list of available for watching videos in RSS format. Service doesn't require authentication and available by `rssToken` returned in Profile API: `https://api.url/rss/d481e7c2-669c-41f1-b8e4-a6c00502b6c1`.
Service doesn't return any personal information (name of the user, Dropbox ID, etc). 
RSS token should be a valid Java UUID string. Service returns `401 Unauthorized` for valid RSS token which doesn't exist in database and `404 Not found` for not valid tokens.

##### Example of request
```bash
curl -XGET --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/rss/d481e7c2-669c-41f1-b8e4-a6c00502b6c1
```

##### Example of response
```xml
<rss xmlns:atom="http://www.w3.org/2005/Atom" xmlns:itunes="http://www.itunes.com/dtds/podcast-1.0.dtd" version="2.0">
    <channel>
    <atom:link href="https://keep.place/rss/" type="application/rss+xml" rel="self"/>
    <itunes:owner>
        <itunes:email>admin@keep.place</itunes:email>
    </itunes:owner>
    <language>en-us</language>
    <itunes:explicit>no</itunes:explicit>
    <link>https://keep.place/</link>
    <title>Keep Place feed</title>
    <description>My Keep.Place feed</description>
    <itunes:summary>My Keep.Place feed</itunes:summary>
    <itunes:author>admin@keep.place</itunes:author>
    <item>
        <title>
            Rails Conf 2012 Keynote: Simplicity Matters by Rich Hickey
        </title>
        <description>
            Rich Hickey, the author of Clojure and designer of Datomic, is a software developer with over 20 years of experience in various domains. Rich has worked on scheduling systems, broadcast automation, audio analysis and fingerprinting, database design, yield management, exit poll systems, and machine listening, in a variety of languages.
        </description>
        <itunes:subtitle>
            Rails Conf 2012 Keynote: Simplicity Matters by Rich Hickey
        </itunes:subtitle>
        <itunes:summary>
            Rich Hickey, the author of Clojure and designer of Datomic, is a software developer with over 20 years of experience in various domains. Rich has worked on scheduling systems, broadcast automation, audio analysis and fingerprinting, database design, yield management, exit poll systems, and machine listening, in a variety of languages.
        </itunes:summary>
        <enclosure url="https://keep.place/data/785b5c17-1bab-4f93-88e9-a39be0123325/download" type="video/mp4"/>
        <link>
            https://keep.place/data/785b5c17-1bab-4f93-88e9-a39be0123325/download
        </link>
        <guid>https://keep.place/videos/101</guid>
        <itunes:author>Confreaks</itunes:author>
        <pubDate>20120501</pubDate>
    </item>
    </channel>
</rss>
```

### Videos

#### Listing all videos
```bash
curl -XGET -H "Content-type: application/json" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/videos
```

#### Viewing details of particular video
```bash
curl -XGET -H "Content-type: application/json" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/videos/132
```

#### Adding video for downloading
```bash
curl -XPOST -H "Content-type: application/json" http://localhost:8080/videos -d  ' { "baseUrl": "youtube" } '
curl -XPOST -H "Content-type: application/json" --cookie "SID=094797c9-b68a-4bb1-822f-017d1e9d62a9" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=e04d8ILXlKw" } '
curl -XPOST -H "Content-type: application/json" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=OsOYg5gpS0s" } '
curl -XPOST -H "Content-type: application/json" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=ApN73TUVMEU" } '
```


#### Removing video (and data files)
```bash
curl -XDELETE -H "Content-type: application/json" http://localhost:8080/videos/9082999699819251973
```
