#ifndef DEEPEYE_RINGBUFFER_H
#define DEEPEYE_RINGBUFFER_H

#include <atomic>
#include <vector>
#include <algorithm>

/**
 * Single Producer Single Consumer (SPSC) Lock-free Ring Buffer
 * Optimized for real-time audio thread safety.
 */
template <typename T>
class RingBuffer {
public:
    explicit RingBuffer(size_t size) 
        : size_(size), 
          buffer_(size),
          head_(0), 
          tail_(0) {}

    // Producer side (Audio thread)
    bool push(const T& item) {
        size_t head = head_.load(std::memory_order_relaxed);
        size_t next_head = (head + 1) % size_;

        if (next_head == tail_.load(std::memory_order_acquire)) {
            return false; // Buffer full
        }

        buffer_[head] = item;
        head_.store(next_head, std::memory_order_release);
        return true;
    }

    size_t push_batch(const T* data, size_t count) {
        size_t written = 0;
        for (size_t i = 0; i < count; ++i) {
            if (push(data[i])) written++;
            else break;
        }
        return written;
    }

    // Consumer side (JNI/UI polling)
    bool pop(T& item) {
        size_t tail = tail_.load(std::memory_order_relaxed);
        if (tail == head_.load(std::memory_order_acquire)) {
            return false; // Buffer empty
        }

        item = buffer_[tail];
        tail_.store((tail + 1) % size_, std::memory_order_release);
        return true;
    }

    size_t pop_batch(T* data, size_t max_count) {
        size_t read = 0;
        for (size_t i = 0; i < max_count; ++i) {
            if (pop(data[i])) read++;
            else break;
        }
        return read;
    }

    size_t available_read() const {
        size_t head = head_.load(std::memory_order_acquire);
        size_t tail = tail_.load(std::memory_order_relaxed);
        if (head >= tail) return head - tail;
        return size_ - (tail - head);
    }

    void clear() {
        head_.store(0, std::memory_order_relaxed);
        tail_.store(0, std::memory_order_release);
    }

private:
    const size_t size_;
    std::vector<T> buffer_;
    std::atomic<size_t> head_;
    std::atomic<size_t> tail_;
};

#endif // DEEPEYE_RINGBUFFER_H
