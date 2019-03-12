# flutter_volume

A flutter plugin as an volume interface for iOS and android.

## Getting Started

Set flutter_volume as dependencie in your pubspec.yaml

## Usage

```dart
import 'package:flutter_volume/flutter_volume.dart';

// Return relative volume as double between 0.0 and 1.0
await FlutterVolume.volume;


// Return mode as int, 0:silent; 1:vibrate; 2:normal
await FlutterVolume.muteMode;

// Sets volume to 0.0
FlutterVolume.mute();

// Set volume between 0.0 and 1.0 as relative value
FlutterVolume.setVolume(0.1);

```

## TODO

- Make it iOS compatible (help is appreciated!)
