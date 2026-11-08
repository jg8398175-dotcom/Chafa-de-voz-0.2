name: Build APK
on:
  workflow_dispatch:
  push:
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: gradle/actions/setup-gradle@v4
        with:
          gradle-version: 8.7
      - name: Compilar
        run: |
          for z in *.zip; do unzip -o "$z" -d work; done
          d=$(dirname "$(find work -name settings.gradle.kts | head -1)")
          cd "$d" && gradle assembleDebug
      - uses: actions/upload-artifact@v4
        with:
          name: McVoice-apk
          path: work/**/app/build/outputs/apk/debug/*.apk
