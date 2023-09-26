天氣預報App - WeatherForecast
===
這是一個 Android 的天氣預報應用程式，提供了即時的天氣情報以及未來 24 小時與每日天氣預報。此外，您還可以搜尋世界各地的城市天氣。
----------------------------------------------------------------------
# App 提供內容
- 即時天氣：隨時查看當前天氣情況，包括溫度、降雨機率和風速。
- 24 小時預報：使用水平滾動列表查看接下來 24 小時的每小時天氣預報。
- 5 日預報：使用垂直滾動列表查看未來 5 日的每日天氣預報。
- 城市搜索：輸入您想要查詢的城市名稱，即可查看該城市的天氣情報。

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
- 初次執行 App 會請求位置資訊，如不同意位置資訊則以台北市信義區經緯度為基準來獲得當前天氣。
- 由於免費 API Key 限制，預報數據只能請求三小時/五日為單位的數據。
- 由於限制，只能獲得 8 筆 24 小時內天氣數據。
- 由於限制，每日天氣數據僅包含 5 筆，是以 GMT+8 時間的 08:00 數據為代表，且這些數據僅能提供每三小時的快照，無法代表整個日子的氣象和溫度變化。
