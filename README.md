# Workspace App — bản thử nghiệm

Đây là bản Android viết lại thử nghiệm dựa trên cấu trúc APK `base.apk` người dùng cung cấp.

## Chức năng hiện tại
- Đăng nhập cục bộ (bất kỳ工号/mật khẩu không rỗng)
- Trang chủ
- Thông tin cá nhân
- Thông tin thiết bị Android
- Nút quét QR / mở camera
- Cài đặt
- Lưu trạng thái đăng nhập

## Build APK bằng GitHub
1. Tạo repository mới trên GitHub.
2. Upload toàn bộ thư mục này lên repository.
3. Vào **Actions → Build Android APK → Run workflow**.
4. Khi hoàn thành, mở job → **Artifacts → workspace-app-debug** và tải APK.
5. Chép APK vào điện thoại Android và cài đặt.

GitHub Actions dùng Gradle 8.11.1 + JDK 17.
