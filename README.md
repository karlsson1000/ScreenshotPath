# Screenshot Path
Tired of losing screenshots when accidentally deleting an instance, or never being able to find them? This mod redirects Minecraft's F2 screenshots to a folder of your choice, outside of your instance. Requires [Fabric API](https://modrinth.com/mod/fabric-api).

### Configuration
On first launch, a config file is created at `.minecraft/config/screenshot-path.json`.
Set your preferred path in the config, or use the in-game command: `/screenshotpath <path>`

**Examples:** `/screenshotpath C:\Users\karlsson\Pictures\Minecraft`,
`/screenshotpath /home/karlsson/Pictures/Minecraft`

```json
//screenshot-path.json
{
  "screenshot_path": "C:\\Users\\karlsson\\Pictures\\Minecraft"
}
```

By default, screenshots are saved to `~/Pictures/Minecraft/screenshots/`.
