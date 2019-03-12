import 'dart:async';

import 'package:flutter/services.dart';

class FlutterVolume {
  static const MethodChannel _channel = const MethodChannel('flutter_volume');

  static Future<int> get muteMode async =>
      (await _channel.invokeMethod("muteMode")) as int;
  static Future<double> get volume async =>
      (await _channel.invokeMethod("volume")) as double;
  static Future setMaxVolume() => _channel.invokeMethod("setMaxVolume");
  static Future setVolume(double volume) =>
      _channel.invokeMethod("setVolume", {"volume": volume});
  static Future mute() => _channel.invokeMethod("mute");
}
