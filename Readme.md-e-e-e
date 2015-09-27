## Development
### Running application
```bash
bin/sbt video-podcast-downloader-rest/run
```

## API
### RSS channel

```bash
curl -XGET -H "Content-type: application/xml" http://localhost:8080/rss
```

### Videos

#### Listing all videos
```bash
curl -XGET -H "Content-type: application/json" http://localhost:8080/videos
```

#### Listing particular video
```bash
curl -XGET -H "Content-type: application/json" http://localhost:8080/videos/132
```

#### Adding video for downloading
```bash
curl -XPOST -H "Content-type: application/json" http://localhost:8080/videos -d  ' { "baseUrl": "youtube" } '
curl -XPOST -H "Content-type: application/json" http://localhost:8080/videos -d  ' { "baseUrl": "https://www.youtube.com/watch?v=e04d8ILXlKw" } '
```
