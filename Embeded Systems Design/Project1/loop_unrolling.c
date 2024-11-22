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

//variables used in main 
int x, y;
int hM = (M/2);
int hN = (N/2);
int hMuv = (M/4);
int hNuv = (N/4);
double sinma, cosma;
int xt, yt;
int xs,ys;

//variables for loop unrolling
int xt1,yt1,xs1,ys1;

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

for(x = 0; x <N; x+=2) {
  for(y = 0; y <M; y+=2) {
    
  int xt = x - hM;
  int yt = y - hN;

  int xt1=(x+1) - hM;
  int yt1 = (y+1) - hN;


  int xs = (int)((sinma * yt + cosma * xt) + hN);
  int ys = (int)((cosma * yt - sinma * xt) + hM);

  int xs1 = (int)((sinma * yt1 + cosma * xt1) + hN);
  int ys1 = (int)((cosma * yt1 - sinma * xt1) + hM);

    if(xs >= 0 && xs < N && ys >= 0 && ys < M) {
      new_y[x][y] = current_y[xs][ys];

    } else {
      new_y[x][y] = 0;
    }

    if(xs1 >= 0 && xs1 < N && ys1 >= 0 && ys1 < M) {
      new_y[x+1][y+1] = current_y[xs1][ys1];

    } else {
      new_y[x+1][y+1] = 0;
    }

  }
}

for(x = 0; x <N; x+=2) {
  for(y = 0; y <M; y+=2) {
    
  int xt = x - hM;
  int yt = y - hN;

  int xt1=(x+1) - hM;
  int yt1 = (y+1) - hN;


  int xs = (int)((sinma * yt + cosma * xt) + hN);
  int ys = (int)((cosma * yt - sinma * xt) + hM);

  int xs1 = (int)((sinma * yt1 + cosma * xt1) + hN);
  int ys1 = (int)((cosma * yt1 - sinma * xt1) + hM);

    if(xs >= 0 && xs < N && ys >= 0 && ys < M) {
      new_u[x][y] = current_u[xs][ys];

    } else {
      new_u[x][y] = 0;
    }

    if(xs1 >= 0 && xs1 < N && ys1 >= 0 && ys1 < M) {
      new_u[x+1][y+1] = current_u[xs1][ys1];

    } else {
      new_u[x+1][y+1] = 0;
    }

  }
}

for(x = 0; x <N; x+=2) {
  for(y = 0; y <M; y+=2) {
    
  int xt = x - hM;
  int yt = y - hN;

  int xt1=(x+1) - hM;
  int yt1 = (y+1) - hN;


  int xs = (int)((sinma * yt + cosma * xt) + hN);
  int ys = (int)((cosma * yt - sinma * xt) + hM);

  int xs1 = (int)((sinma * yt1 + cosma * xt1) + hN);
  int ys1 = (int)((cosma * yt1 - sinma * xt1) + hM);

    if(xs >= 0 && xs < N && ys >= 0 && ys < M) {
      new_v[x][y] = current_v[xs][ys];

    } else {
      new_v[x][y] = 0;
    }

    if(xs1 >= 0 && xs1 < N && ys1 >= 0 && ys1 < M) {
      new_v[x+1][y+1] = current_v[xs1][ys1];

    } else {
      new_v[x+1][y+1] = 0;
    }

  }
}

write();
}


