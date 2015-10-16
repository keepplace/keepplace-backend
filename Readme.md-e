## Get started
### Installing deps
```bash
brew install youtube-dl ffmpeg libav
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
  --url="jdbc:h2:./test" \
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

Open in browser URL:
```
http://localhost:8080/auth/login
```

#### Profile
```bash
curl -XGET --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/auth/profile
```

### RSS channel

```bash
curl -XGET -H "Content-type: application/xml" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/rss
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
