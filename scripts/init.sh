#!/bin/bash

echo "🚀 Bắt đầu khởi tạo hệ thống Leave Request Approval..."

# 1. Start database và RabbitMQ trước
echo "⏳ Đang khởi động DB, Auth Service và RabbitMQ..."
docker-compose up -d balance-service-db employee-service-db request-service-db rabbitmq auth-service

echo "🕒 Chờ DB, Auth Service và RabbitMQ khởi động..."
sleep 5

# 2. Start các service phụ thuộc DB, Auth Service và RabbitMQ
echo "📦 Đang khởi động balance-service, employee-service, request-service, và gmail-service..."
docker-compose up -d balance-service employee-service request-service gmail-service

echo "🕒 Chờ service phụ khởi động..."
sleep 5

# 3. Start Leave Request Service (phụ thuộc nhiều service)
echo "🗂️  Đang khởi động Leave Request Service..."
docker-compose up -d leave-request-service

echo "🕒 Chờ Leave Request Service khởi động..."
sleep 5

# 4. Cuối cùng, start Gateway
echo "🌐 Đang khởi động Gateway..."
docker-compose up -d gateway

sleep 5

echo "✅ Hệ thống đã khởi động xong!"
