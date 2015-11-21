#include<stdio.h>
#include<string.h>

int main()
{
int k=createSFS("myfile1.txt",1024);
FILE* f = fdopen(k, "r+");
char ch;
char str[34];
fseek(f,0,SEEK_SET);

//ch=fgetc(f);
		//printf("val %s\n",str);
int m;
for (m = 0; m < 256; m++) {
 		ch=fgetc(f);
		printf("%c",ch);
		
		}

//writeFile(k,"saywhat.txt", "hsahdahdadhd hasdbashd ashdvashd");

//print_inodeBitmaps(k);

}

int createSFS( char* filename, int nbytes)// – returns a fileSystemId 
{
FILE *fp=fopen(filename, "w");
fseek(fp, nbytes-1, SEEK_SET);
fputc('\n', fp);
fclose(fp);

int blocksize=32;
int blocks = nbytes/32;

int inodebitmap=1;
int databitmap=3;

int superblock=1;

fp=fopen(filename, "r+");

char a[34]="00000000000000000000000000000000";

fseek(fp, 0, SEEK_SET);
fprintf(fp,"%d ",blocksize);
fprintf(fp,"nb%d ",inodebitmap);
fprintf(fp,"db%d ",databitmap);
fprintf(fp,"i%d ",4);

fprintf(fp,"t%d",9);

fseek(fp, blocksize*inodebitmap, SEEK_SET);
fwrite(a , 1 , strlen(a) , fp );

fseek(fp, blocksize*(inodebitmap+2), SEEK_SET);
fwrite(a , 1 , strlen(a) , fp );

return fileno(fp);
}
