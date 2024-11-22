#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>

#define N 330 /* height */
#define M 440 /* width */
#define filename "dog_440x330.yuv"
#define file_yuv "teleiothta3.yuv"
#define PI 3.14159265

/* code for armulator*/
#pragma arm section zidata="ram"
int current_y[N][M];
int current_u[N/2][M/2];
int current_v[N/2][M/2];
#pragma arm section

double angle =120;

int i,j;
int new_y[N][M];
int new_u[N/2][M/2];
int new_v[N/2][M/2];


	int x, y;
    int hM = (M/2);
    int hN = (N/2);
    int hMuv = (M/4);
    int hNuv = (N/4);
	double sinma, cosma;
    int xt, yt;
    int xs,ys;

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

for(x = 0; x < N; x++) {
	for(y = 0; y < M; y++) {
		
	int xt = x - hM;
    int yt = y - hN;


    int xs = (int)((sinma * yt + cosma * xt) + hN);
    int ys = (int)((cosma * yt - sinma * xt) + hM);

		if(xs >= 0 && xs < N && ys >= 0 && ys < M) {
			new_y[x][y] = current_y[xs][ys];
      		new_u[x][y] = current_u[xs][ys];
     		 new_v[x][y] = current_v[xs][ys];

		} else {
			new_y[x][y] = 0;
      		new_u[x][y] = 0;
      		new_v[x][y] = 0;
		}
	}
}

write();
}

