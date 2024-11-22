#include <stdio.h>
#include <math.h>
#include <stdlib.h>

#define N 330 /* height */
#define M 440 /* width */
#define filename "dog_440x330.yuv"
#define file_yuv "teleiothta2.yuv"
#define PI 3.14159265

/* code for armulator*/
#pragma arm section zidata="manual"
int i;
int j;
int x;
int y;
double sinma;
double cosma;
double angle =54;
#pragma arm section

int current_y[N][M];
int current_u[N/2][M/2];
int current_v[N/2][M/2];
int new_y[N][M];
int new_u[N/2][M/2];
int new_v[N/2][M/2];

void read()
{
  FILE *frame_c;
  if((frame_c=fopen(filename,"rb"))==NULL)
  {
    printf("current frame doesn't exist\n");
    exit(-1);
  }

  for(i=0;i<N;i++)
  {
    for(j=0;j<M;j++)
    {
      current_y[i][j]=fgetc(frame_c);
    }
  }
    for(i=0;i<N/2;i++)
  {
    for(j=0;j<M/2;j++)
    {
      current_u[i][j]=fgetc(frame_c);
    }
  }
  for(i=0;i<N/2;i++)
  {
    for(j=0;j<M/2;j++)
    {
      current_v[i][j]=fgetc(frame_c);
    }
  }

  fclose(frame_c);
}

void write()
{
  FILE *frame_yuv;
  frame_yuv=fopen(file_yuv,"wb");

  for(i=0;i<N;i++)
  {
    for(j=0;j<M;j++)
    {
      fputc(new_y[i][j],frame_yuv);
    }
  }
    for(i=0;i<N/2;i++)
  {
    for(j=0;j<M/2;j++)
    {
      fputc(new_u[i][j],frame_yuv);
    }
  }

  for(i=0;i<N/2;i++)
  {
    for(j=0;j<M/2;j++)
    {
      fputc(new_v[i][j],frame_yuv);
    }
  }

  fclose(frame_yuv);
}

int main()
{
    read();
    
    sinma = sin(PI*angle/180);
    cosma = cos(PI*angle/180);

for(x=N/2; x--;) {
	for(y=M/2; y--;) {
		
        int xs1 = (int)((sinma * (y*2 - N/2) + cosma * (x*2 - M/2)) + N/2);
	    int xs2 = (int)((sinma * (y*2+1 - N/2) + cosma * (x*2+1 - M/2)) + N/2);
        int ys1 = (int)((cosma * (y*2 - N/2) - sinma * (x*2 - M/2) + M/2));
	    int ys2 = (int)((cosma * (y*2+1 - N/2) - sinma * (x*2+1 - M/2) + M/2));
	
		if(xs1 >= 0 && xs1 < N && xs2 >= 0 && xs2 < N && ys1 >= 0 && ys1 < M && ys2 >= 0 && ys2 < M) {
			new_y[x*2][y*2] = current_y[xs1][ys1];
			new_y[x*2+1][y*2+1] = current_y[xs2][ys2];

		} else {
			new_y[x*2][y*2] = 0;
			new_y[x*2+1][y*2+1] = 0;
		}	
	}
}

for(x=N/2; x--;) {
	for(y=M/2; y--;) {

    int xs = (int)((sinma * (y - N/4) + cosma * (x - M/4)) + N/4);
    int ys = (int)((cosma * (y - N/4) - sinma * (x - M/4)) + M/4);

		if(xs >= 0 && xs < N/2 && ys >= 0 && ys < M/2) {
			new_u[x][y] = current_u[xs][ys];
			new_v[x][y] = current_v[xs][ys];

		} else {
			new_u[x][y] = 0;
			new_v[x][y] = 0;
		}
	}
}

write();
}

