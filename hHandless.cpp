#include <bits/stdc++.h>
#include <windows.h>

void routine(int *n) 
{
  printf("my argument is %d\n", *n);
}
int main() 
{
  int p[5], q[5], i;
  DWORD id;
  HANDLE hHandless[5];
  for(i = 0; i < 5; ++i)
  {
    p[i] = i;
    q[i] = 2*i;
    hHandless[i] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)routine,&p[i], WAIT_TIMEOUT, &id);
    printf("Thread %d was created:\n", id);
  }
  for(i = 0; i < 5; ++i)
  {
    WaitForSingleObject(hHandless[i], INFINITE);
  }
  return 0;
}
