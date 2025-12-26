\Smart Rate Limiter
Overview
A full-stack simulation tool designed to visualize and test distributed rate-limiting algorithms. This project demonstrates high-concurrency request handling using custom Java implementations of Token Bucket, Leaky Bucket, and Fixed Window algorithms, paired with a real-time React visualization dashboard.

Technology Stack
Backend: Java (Spring Boot), Custom Concurrency Implementation

Frontend: React.js, Tailwind CSS

Visualization: Chart.js, Axios

Architecture: REST API

Algorithms
Token Bucket: Manages bursts while maintaining an average rate.

Leaky Bucket: Enforces a constant output rate regardless of input traffic.

Fixed Window: Limits total requests within a specific time block.

API Reference
Endpoint: POST /sim/request



