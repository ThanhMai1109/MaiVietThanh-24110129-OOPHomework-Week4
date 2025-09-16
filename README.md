# 🚍 Hệ thống Quản lý Trạm Giao thông Công cộng

## 📌 Giới thiệu
Đây là một project **C++ hướng đối tượng (OOP)** mô phỏng hệ thống quản lý trạm giao thông công cộng cơ bản.  
Hệ thống có thể quản lý:  
- **Trạm (Station)**: gồm thông tin tên trạm, loại trạm (bus/train), lịch trình đến/đi.  
- **Phương tiện (Vehicle)**: xe buýt thường, xe buýt nhanh (ExpressBus).  
- **Hành khách (Passenger)**: đặt chỗ, huỷ chỗ.  
- **Lịch trình (Schedule)**: thông tin thời gian khởi hành và đến nơi.  

Mục tiêu chính của project là minh hoạ **tính kế thừa, đa hình, đóng gói** trong OOP.

---

## 🏗️ Thiết kế OOP
- **Lớp Vehicle**: quản lý tuyến đường, sức chứa, số ghế đã đặt.  
- **Lớp ExpressBus**: kế thừa từ `Vehicle`, bổ sung thuộc tính tốc độ.  
- **Lớp Station**: quản lý lịch trình, liên kết với các phương tiện.  
- **Lớp Passenger**: cho phép đặt/huỷ chỗ trên phương tiện.  
- **Lớp Schedule**: lưu thông tin về thời gian (giờ đi, giờ đến).  

Quan hệ kế thừa:  
```
Vehicle  <── ExpressBus
```

---

## ⚙️ Cách chạy chương trình
1. Biên dịch:
```
g++ main.cpp -o transport
```
2. Chạy chương trình:
```
./transport
```

---

## 🧪 Kết quả kiểm thử
Ví dụ output:  
```
[TC1] Booking seats on Express bus:
Booked successfully
Passenger: Alice, ID: P001
Vehicle: Express 101, Capacity: 40, Booked: 1
```

- **TC1**: Kiểm tra đặt chỗ thành công.  
- **TC2**: Kiểm tra huỷ chỗ.  
- **TC3**: Kiểm tra vượt quá sức chứa.  
- **TC4**: Kiểm tra nhiều lịch trình cho cùng một phương tiện.  

---

## 🤖 Sử dụng AI hỗ trợ
Trong quá trình làm project, mình có sử dụng **ChatGPT** để:  
- Gợi ý quan hệ kế thừa trong mô hình OOP.  
- Hỗ trợ chỉnh sửa lỗi constructor (`bookseat` bị giá trị rác).  
- Gợi ý cách viết tài liệu (PDF + README).  

Lưu ý: **toàn bộ code được viết thủ công**, AI chỉ dùng để tham khảo và giải thích.  

---

## 📚 Tham khảo
- Giáo trình Lập trình Hướng đối tượng – C++  
- Tài liệu OOP cơ bản  
- ChatGPT (OpenAI) – hỗ trợ giải thích và tài liệu hoá  
