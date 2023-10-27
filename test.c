#include <stdio.h>
int main()
{
	printf("Hello, World!\n");
	for (int i = 0; i < 5; i++)
	{
		if (i % 2 == 0)
		{
			printf("Even: %d\n", i);
		}
		 else
		{
			 printf("Odd: %d\n", i);
		}
	}
	return 0;
}
