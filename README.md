Warning - Trauma Dump ahead.
PS - Check the branch for the codebase.

# 🚦 Smart Rate Limiter

### *Because 429 "Too Many Requests" is my love language.*

---

## 🤯 Why? (The "Sanity is Overrated" Edition)

Look, I could have just used a pre-built library. I could have written this in Python and been done in 10 minutes. I could have just used a simple counter in memory.

**But where is the suffering in that?**

I chose **Java** because I apparently hate being relaxed.
I chose **Redis** and **GraphQL** not because they were strictly necessary for a simple simulation, but because I wanted to feel the distinct sensation of my sanity leaving my body at 3 AM.

This project is a testament to over-engineering, caffeine dependency, and the sheer thrill of building high-concurrency rate limiting algorithms from scratch while wondering why I do this to myself.

---

## 🚀 About The Project

Smart Rate Limiter is a full-stack simulation tool designed to visualize how different rate-limiting algorithms handle traffic bursts. It features a **Spring Boot** backend that implements core algorithms manually (no shortcuts!) and a **React** frontend that visualizes accepted vs. denied requests in real-time.

It’s perfect for understanding how distributed systems protect themselves from DDOS attacks, noisy neighbors, and over-eager API consumers.

## 🛠️ Tech Stack

* **Backend:** Java (Spring Boot) - *The heavy lifter.*
* **Frontend:** React.js + Tailwind CSS - *Because it has to look pretty while it rejects you.*
* **Visualization:** Chart.js - *To plot the graph of your failure (and success).*
* **Data/State:** Redis - *For distributed counting (and distributed headaches).*
* **API:** REST & GraphQL - *Why choose one when you can struggle with both?*

---

## 🧠 Algorithms Implemented

I didn't just `import RateLimiter`. I built the logic to ensure maximum control (and maximum bugs... I mean features).

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
