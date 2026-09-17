# HDB

[繁體中文](#荷包蛋)

A minimal, ultra-lightweight (~61 KB) Android app that easily manages `adb_enabled` to bypass financial app debug detection.

## TL;DR for Android Developers & Power Users
If you are tired of constantly digging into system settings to turn off USB Debugging every single time you need to open a banking or payment app, HDB is built for you. It is a zero-dependency, ultra-lightweight (~61 KB) utility that tricks stubborn financial apps into thinking ADB is disabled, allowing you to use your mobile banking seamlessly without breaking your development workflow. No root or Magisk modules required.

## Setup & Permission

To use HDB, install the APK and grant the `WRITE_SECURE_SETTINGS` permission via ADB (one-time setup):

### 1. Install

```bash
adb install hdb-v1.1.0.apk
```

### 2. Grant Permission

`WRITE_SECURE_SETTINGS` cannot be granted through normal installation. You must grant it via ADB (one-time):

```bash
adb shell pm grant dev.e88e89.hdb android.permission.WRITE_SECURE_SETTINGS
```

Once granted, you can toggle `adb_enabled` directly from the Quick Settings panel or inside the app.

## How It Works

Many financial/banking apps check `Settings.Global.ADB_ENABLED == 1` to detect USB debugging. By setting the value to 2, these simple equality checks fail — the app thinks ADB is off, while Android still treats any non-zero value as enabled.

HDB provides a quick and native way to manage this setting.

## Features

- **Quick Settings Tile**: Toggle ADB directly from the quick settings panel
- **Configurable value**: Choose between 0, 1, or 2
- **Pure Native UI**: No heavy Material or AppCompat dependencies, reducing APK size to ~61 KB
- **i18n**: English and Traditional Chinese

## How the Bypass Works

| adb_enabled | ADB Functional | Financial App Detection |
|:-:|:-:|:-:|
| 0 | No | Passes (ADB off) |
| 1 | Yes | Detected |
| 2 | Yes | Passes (≠ 1) |

## Compatibility

Tested with `adb_enabled = 2`:

The vast majority of Taiwanese banking and payment apps pass detection without issues.

Note: Apps that use more sophisticated detection methods (e.g. checking USB connection state, `ro.debuggable`, or using attestation APIs) may still detect debugging regardless of the `adb_enabled` value.

## Building from Source

Requires Java 17 and Android SDK.

```bash
./gradlew assembleRelease
```

The compiled APK will be output to `app/build/outputs/apk/release/app-release.apk`.

## License

This project is licensed under the GNU General Public License v3.0.

---

# 荷包蛋

[English](#hdb)

## 給開發者與玩機玩家的懶人包
如果你受夠了每次打開網銀或行動支付 APP，都得大費周章跑去系統設定把 USB 偵錯關掉，用完再重新打開，這款工具就是為你打造的。荷包蛋是一個完全無第三方依賴、體積僅約 61 KB 的超輕量小工具，能讓你在不破壞開發工作流的前提下，欺騙金融 APP 的偵錯偵測，從此轉帳刷支付再也不卡頓。免 Root、免 Magisk 模組。

| 應用程式介面 | 快速設定面板 |
| :---: | :---: |
| <img src="Screenshot1.png" width="300" alt="應用程式介面" /> | <img src="Screenshot2.png" width="300" alt="快速設定面板" /> |

## 安裝與授權設定

使用本 APP 需先安裝 APK，並透過 ADB 授予 `WRITE_SECURE_SETTINGS` 權限（僅需授權一次）：

### 1. 安裝 APK

```bash
adb install hdb-v1.1.0.apk
```

### 2. 授予權限

`WRITE_SECURE_SETTINGS` 無法透過一般安裝取得，必須透過 ADB 授權（一次性）：

```bash
adb shell pm grant dev.e88e89.hdb android.permission.WRITE_SECURE_SETTINGS
```

完成授權後，即可透過狀態列的「快速設定面板 (Quick Settings Tile)」或在 APP 內直接切換 `adb_enabled` 狀態。

## 原理

許多金融與銀行 APP 透過 `Settings.Global.ADB_ENABLED == 1` 偵測 USB 偵錯模式。將值設為 2，這些簡單的相等檢查就會失敗，APP 認為 ADB 已關閉，但 Android 系統仍將非零值視為啟用。

荷包蛋 (HDB) 提供最簡單原生的方式來切換此狀態。

## 功能

- **快速設定面板 (Quick Settings Tile)**: 支援在狀態列下拉選單中直接切換狀態
- **可自訂數值**: 可選擇 0、1 或 2
- **純原生 UI**: 無多餘的 Material 或 AppCompat 依賴，APK 體積縮減至 ~61 KB
- **多語言支援**: 英文及繁體中文

## 繞過原理

| adb_enabled | ADB 可用 | 金融 APP 偵測 |
|:-:|:-:|:-:|
| 0 | No | 通過 (ADB 關閉) |
| 1 | Yes | 被偵測 |
| 2 | Yes | 通過 (≠ 1) |

## 相容性

以 `adb_enabled = 2` 測試:

| APP | 狀態 | 備註 |
|---|:-:|---|
| iPASS MONEY (一卡通) | 通過 | |
| 全支付 | 通過 | |
| 悠遊付 (Easy Wallet) | 通過 | |
| 台北富邦銀行 | 通過 | |
| 富邦 AI Pro | 通過 | |
| 行動郵局 | 通過 | |
| 街口支付 | 通過 | |
| 國泰世華 | 通過 | |
| 中國信託 | 通過 | |
| OPEN POINT | 通過 | |
| 國泰證券 | 警告 | 會顯示警告，但不影響使用 |
| 將來銀行 | 警告 | 轉帳功能受限 |
| 全家便利商店 | 阻擋 | 拒絕啟動 |

注意: 使用更進階偵測方式的 APP (如檢查 USB 連線狀態、`ro.debuggable` 或使用 attestation API) 可能仍會偵測到偵錯模式，不受 `adb_enabled` 值影響。

## 建構教學 (Build)

自行建構需要 Java 17 及 Android SDK：

```bash
./gradlew assembleRelease
```

編譯完成的 APK 位於 `app/build/outputs/apk/release/app-release.apk`。

## 授權條款

本專案採用 GNU General Public License v3.0 授權。
