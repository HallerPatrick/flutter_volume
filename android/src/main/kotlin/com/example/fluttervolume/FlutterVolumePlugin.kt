package com.example.fluttervolume

import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry.Registrar

import android.content.Context
import android.media.AudioManager

class FlutterVolumePlugin private constructor(private val _registrar: Registrar) : MethodCallHandler {

  private val volume: Double
    get() {
      val am = _registrar.activeContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
      val maxValue = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toDouble()
      return am.getStreamVolume(AudioManager.STREAM_MUSIC) / maxValue
    }

  private val muteMode: Int
    get() {
      val am = _registrar.activeContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
      return am.getRingerMode();
    }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
      "muteMode" -> result.success(muteMode)
      "volume" -> result.success(volume)
      "setMaxVolume" -> setMaxVolume()
      "setVolume" -> {
        var vol:Double = 1.0
        if( call.hasArgument("volume")){
          vol = call.argument<Double>("volume")!!.toDouble()
        }
        setVolume(vol)
      }
      "mute" -> setMinVolume()
    }
  }

  private fun setVolume(volume: Double) {
    val am = (_registrar.activeContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager)
    val maxValue = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    am.setStreamVolume(AudioManager.STREAM_MUSIC, (maxValue * volume + 0.5).toInt(), 0)
  }

  private fun setMaxVolume() {
    val am = (_registrar.activeContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager)
    am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0)
  }

  private fun setMinVolume() {
    val am = _registrar.activeContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
    am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
  }

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar): Unit {
      val channel = MethodChannel(registrar.messenger(), "flutter_volume")
      channel.setMethodCallHandler(FlutterVolumePlugin(registrar))
    }
  }
}
