## Get started
### Installing deps
```bash
brew install youtube-dl ffmpeg libav
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
curl -XPOST -H "Content-type: application/json" --cookie "SID=9a8e3a31-06b5-4a86-b8cf-021c45dcda8b" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=e04d8ILXlKw" } '
curl -XPOST -H "Content-type: application/json" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=OsOYg5gpS0s" } '
curl -XPOST -H "Content-type: application/json" --cookie "SID=5b73e1e6-0bc5-47bc-b1bd-ce3fb1336507" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=ApN73TUVMEU" } '
```


#### Removing video (and data files)
```bash
curl -XDELETE -H "Content-type: application/json" http://localhost:8080/videos/9082999699819251973
```
