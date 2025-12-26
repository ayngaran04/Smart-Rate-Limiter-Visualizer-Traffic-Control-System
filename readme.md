# 🚦 Smart Rate Limiter

### *Because 429 "Too Many Requests" is my love language.*

---

## 🤯 Why? (The "Reinventing the Wheel" Edition)

Look, I could have just used `Bucket4j`. I could have used `Guava`. I could have just let the load balancer handle it like a normal person.

**But where is the character development in that?**

I chose **Java** to write this because I apparently hate being relaxed.
I decided to implement raw concurrency control and thread-safe logic manually—not because it's efficient, but because I wanted to feel the thrill of potential race conditions at 2 AM.

This project is a testament to over-engineering, caffeine dependency, and the sheer audacity of building rate-limiting algorithms from scratch just to see if I could.

---

## 🚀 About The Project

Smart Rate Limiter is a full-stack simulation tool designed to visualize how different rate-limiting algorithms handle traffic bursts.

It features a **Spring Boot** backend that implements core algorithms using pure Java concurrency (no cheating with external libraries!) and a **React** frontend that visualizes the exact moment your requests get rejected.

It’s perfect for understanding how distributed systems protect themselves from DDOS attacks, noisy neighbors, and people who click "Submit" five times in one second.

## 🛠️ Tech Stack

* **Backend:** Java (Spring Boot) - *Running on pure caffeine and `ConcurrentHashMap`.*
* **Frontend:** React.js + Tailwind CSS - *Because it has to look pretty while it rejects you.*
* **Visualization:** Chart.js - *To plot the graph of your failure (and success).*
* **Concurrency:** `synchronized` blocks & thread-safe queues - *Because I like living dangerously.*
* **API:** REST - *Good old reliable JSON.*

---

## 🧠 Algorithms Implemented

I didn't just import a library. I wrote the logic to ensure maximum control (and maximum opportunity for bugs... I mean features).

### 1. 🪙 Token Bucket
* **Concept:** Tokens are added to a bucket at a fixed rate. Requests need a token to proceed.
* **Best for:** Allowing short bursts of traffic while maintaining an average rate.

### 2. 🚰 Leaky Bucket
* **Concept:** Requests enter a queue and are processed (leaked) at a constant rate. If the queue is full, new requests are dropped.
* **Best for:** Smoothing out bursty traffic into a stable, constant flow.

### 3. 🪟 Fixed Window Counter
* **Concept:** The timeline is divided into fixed windows (e.g., 1 minute). A counter tracks requests per window.
* **Best for:** Simple use cases, though it suffers from the "thundering herd" problem at window edges.

---

## 🔌 API Reference

The simulation is driven by a main POST endpoint.

### Simulate Request
**Endpoint:** `POST /sim/request`

**Body:**
```json
{
  "clientId": "user_panic_mode",
  "algorithm": "TokenBucket",
  "capacity": 10,
  "rate": 5
}

