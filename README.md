天氣預報App - WeatherForecast
===
這是一個多功能的天氣預報應用程式，提供了即時的天氣情報以及未來 24 小時和 5 天的天氣預報。此外，您還可以輕鬆搜尋世界各地的城市天氣。
---
# App 提供內容
1. 當前天氣
2. 橫向列表的 24 小時內天氣
3. 縱向列表的每日天氣
4. 搜尋其他城市天氣

# 測驗要求
#### 應用程式符合以下測試要求：
- [x] 使用 Kotlin 語言開發
- [x] 遵循 MVVM 架構設計
- [x] 利用 Kotlin Flow、Retrofit 和 Hilt 實現 API 數據交互 [data](app/src/main/java/com/example/weatherforecast/data)
- [x] 使用 Jetpack Compose 和 Hilt 實現現代化的 UI 介面 [compose](app/src/main/java/com/example/weatherforecast/compose)
- [x] 支援不同螢幕尺寸的自適應布局
- [x] 提供錯誤處理機制，當網路連接中斷或 API 請求失敗時，會彈出 SnackBar 通知並提供錯誤代碼
- [x] 添加舒適的背景動畫效果
- [x] 編寫單元測試以驗證日期處理和資料處理工具的正確性 [test](app/src/test)

# 注意事項
#### 請注意以下事項：
1. 由於免費 API Key 限制，預報數據只能請求三小時/五日為單位的數據。
2. 由於限制，只能獲得 8 筆 24 小時內天氣數據。
3. 由於限制，每日天氣數據僅包含 5 筆，是以 GMT+8 時間的 08:00 數據為代表，且，這些數據僅能提供每三小時的快照，無法代表整個日子的氣象和溫度變化。
