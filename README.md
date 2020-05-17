# Avalon

## Development state

### PermissionHelper.kt

![Permission](./gifs/PermissionHelper.gif)

### Title.kt

![Title](./gifs/Title.gif)

### ChoiceCountDownView.kt

![CD](./gifs/ChoiceCountDownView.gif)

#### Usage

```kotlin
// cd is the class
val p = Player(/* player name = */ name).apply {
  // Thus can prevent good side from making
  // mission failed
  character = Mordred(/* context = */ context)
}
cd.setPlayer(p)
cd.startCountDown(ChoiceCountDownView.MISSION, /* seconds = */ 480, callback)
```
