#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <pthread.h>
#define BUFFER_SIZE 5
#define MAX 30

int buffer[BUFFER_SIZE], count_in = MAX, count_out = MAX, counter = 0;
int in = 0, out = 0, a = 1, b = 1;
HANDLE buff;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t full = PTHREAD_COND_INITIALIZER;
pthread_cond_t empty = PTHREAD_COND_INITIALIZER;

void *Producer(void *data)
{
	while (count_in)
	{
		pthread_mutex_lock(&mutex);
		while (counter == BUFFER_SIZE)
		{
			printf("Queue full\n");
			pthread_cond_wait(&empty, &mutex);
		}
		count_in--;
		buffer[in] = rand() % 100;
		printf("Next producer %d was complete\n", in + 1);
		in = (in + 1) % BUFFER_SIZE;
		counter++;
		pthread_mutex_unlock(&mutex);
		pthread_cond_signal(&full);
	}
}
void *Consumer(void *data)
{
	while (count_out)
	{
		pthread_mutex_lock(&mutex);
		while (counter == 0)
		{
			printf("Queue free\n");
			pthread_cond_wait(&full, &mutex);
		}
		count_out--;
		printf("Next consumer %d: %d\n", out + 1, buffer[out]);
		out = (out + 1) % BUFFER_SIZE;
		counter--;
		pthread_mutex_unlock(&mutex);
		pthread_cond_signal(&empty);
	}
}

int main()
{
	pthread_t ptid, ctid;
	pthread_create(&ptid, NULL, Producer, NULL);
	pthread_create(&ctid, NULL, Consumer, NULL);

	pthread_join(ptid, NULL);
	pthread_join(ctid, NULL);
	return 0;
}