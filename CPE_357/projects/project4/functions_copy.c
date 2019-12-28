#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <time.h>
#include <pwd.h>
#include <grp.h>
#include <sys/types.h>
#include <sys/stat.h> 
#include <unistd.h>
#define B_SIZE 512
#define NAME_SIZE 100

int is_dir(char* path);
int header(char* path, char* pre, int file);
int body(char* path, int file);


struct extract_file{
   char* fullname;
   char* prefix;
   char* name;
   char* premissions;
   char* type;
   char* time;   
};	
 

int create(char* name, int file, int verb){
   char* path = malloc(256);
   char* ptr;
   char* prefix = malloc(155);
   int length;
   int ll;
   if(strlen(name)>256){fprintf(stderr, "Please use paths no longer than 256.\n");
      return 0;
   }
   char* shortpath = malloc(100);
   char* modpath = malloc(256);
   int zerocount;
   int i = 0;
   /*int set = 0;*/
   ptr = name;
      
   for(i = 0; name[i] != 0; i++){}
      if(name[i-1] == '/'){name[i-1] = 0;}

   if(is_dir(ptr)){
      sprintf(modpath, "%s/", ptr);
      }
   else{
      sprintf(modpath, "%s", ptr);
      }
    length = strlen(modpath);
    if(length <= 100){
    write(file, modpath, 100);}
    else{
       ll = length -100;
       for(i = 0; i!=length; i++){
          if(i<ll){
          prefix[i] = modpath[i];}
          else{
          shortpath[i-ll]=modpath[i];
          }
       }
    prefix[ll] = 0;
    shortpath[100] = 0;
    write(file, shortpath, 100);
    }
    if(verb == '1'){printf("%s\n",modpath);}


    if(ll!=0){
    header(modpath, prefix,file);
    }
    else{
    header(modpath, NULL, file);
    }

    if(is_dir(modpath) == 0){
    body(modpath, file);}

        	
   if(is_dir(ptr)){
      DIR* dirp = opendir(ptr);
      struct dirent *dp;
      while(dp = readdir(dirp)){
         modpath = malloc(256);
         if(strncmp(dp->d_name, ".", 1)!= 0){
            sprintf(modpath, "%s/%s",ptr,dp->d_name);
            /*for(i = 0; modpath[i] != '\0'; i++){
               printf("%d %c\n", i, modpath[i]);
            }*/
            create(modpath, file, verb);
         }
         free(modpath);


      }


   }

   return 0;

}

int is_dir(char *path)
{
    struct stat path_stat;
    stat(path, &path_stat);
    return S_ISDIR(path_stat.st_mode);
}

int f_size(char *path)
{
    struct stat path_stat;
    stat(path, &path_stat);
    return path_stat.st_size;


}

int header(char *path, char* pre, int file)
{
    char* mode = malloc(9);
    char* buff = malloc(1);
    int i;
    int num = 1;
    int numm = 0;
    int sum = 0;
    /*long int name = 0;*/
    char* buf = malloc(1);
    char* binary = malloc(32);
    struct stat path_stat;
    struct passwd *pw;
    struct group *grp;
    lstat(path, &path_stat); 

    sprintf(mode, "%07o", 07777 & path_stat.st_mode);
    write(file, mode, 8);
    free(mode);

    




    mode = malloc(9);
    

    if((int)path_stat.st_uid >= 2097151){
       sprintf(mode, "%07o", path_stat.st_uid);
       mode[8] = 0;
       for(i=0; i!=8; i++){
           mode[i]=mode[i+1];
       }
       mode[7] = 0;
      
    }
    else{
       sprintf(mode, "%07o", path_stat.st_uid);
       }


    write(file, mode, 8);
    free(mode);


    mode = malloc(9);
    sprintf(mode, "%07o", path_stat.st_gid);
    write(file, mode, 8);
    free(mode);

    mode = malloc(13);
    if(S_ISDIR(path_stat.st_mode) || S_ISLNK(path_stat.st_mode)){
    sprintf(mode, "%011o",0);
    }
    else{
    sprintf(mode, "%011o", path_stat.st_size);
    }
    write(file, mode, 12);
    free(mode);

    mode = malloc(13);
    long int time = path_stat.st_mtime;
    sprintf(mode, "%o", time);
    write(file, mode, 12);
    free(mode);

    write(file, "        ",8);
    
    mode = malloc(2);
    if(S_ISDIR(path_stat.st_mode)){
      sprintf(mode, "5");
    }
    else if(S_ISLNK(path_stat.st_mode)){
       sprintf(mode, "2");
    }
    else{
       sprintf(mode, "0");
    }
    write(file, mode, 1);
    free(mode);

    mode = malloc(101);
    if(S_ISLNK(path_stat.st_mode)){
       realpath(path, mode);
       write(file, mode, 100);
    }
    else{
    for(i = 0; i!= 100; i++){
       mode[i] = 0;
    }
    write(file, mode, 100);
    }
    mode = NULL;
    free(mode);
    
    write(file, "ustar", 6);
    write(file, "00", 2);
    

    mode = malloc(32);
    pw = getpwuid(path_stat.st_uid);
    grp = getgrgid(path_stat.st_gid);
    sprintf(mode, "%s", pw->pw_name);
    numm = strlen(mode);
    write(file, mode, numm);
    mode = NULL;
    free(mode);
    mode = malloc(32-numm);
    for(i = 0; i!=(32-numm); i++){
       mode[i] = 0; 
    }
    write(file, mode, 32-numm);
    mode = NULL;
    free(mode);

    mode = malloc(32);
    sprintf(mode, "%s", grp->gr_name);
    write(file, mode, strlen(mode));
    numm = strlen(mode);
    mode = NULL;
    free(mode);
    mode = malloc(32-numm);
    for(i = 0; i!=(32-numm); i++){
       mode[i] = 0; 
    }
    write(file, mode, 32-numm);
    mode = NULL;
    free(mode);


    mode = malloc(8);
    for(i = 0; i!=(8); i++){
       mode[i] = 0; 
    }
    write(file, mode, 8);
    write(file, mode, 8);
    mode = NULL;
    free(mode);
    
    if(pre == NULL){
    mode = malloc(156);
    if(strlen(path) > 100){
       for(i = 0; i!= 155; i++){
          mode[i] = path[i+100];    
       }}
    else{
    for(i = 0; i!= 155; i++){
       mode[i] = 0;
    }}
    write(file, mode, 155); 
    free(mode);}
    else{
    write(file, pre, 155);
    }
    
    mode = malloc(13);
    for(i = 0; i!= 12; i++){
       mode[i] = 0;
    }
    write(file, mode, 12);
    free(mode);

    mode = malloc(8);
    lseek(file, -512, SEEK_CUR);
    while(num == 1){
       num = read(file, buf, 1);
       if(num != 0){
          sum += buf[0];
       }
    }
    sprintf(mode,"%07o",sum);
    lseek(file, -364, SEEK_CUR);
    write(file, mode, 8);
    lseek(file, 0, SEEK_END);
   pw = NULL;
   free(pw);
   grp = NULL;
   free(grp);
    return 0;
}

int body(char* path, int file){
   int num = 512;
   int i = 0;
   FILE *rfile = fopen(path, "r");
   char* buf = malloc(512);
   while(num == 512){
      num = fread(buf, 1, 512, rfile);
      if(num != 0){
         write(file, buf, num);  
      }
   }
   buf = NULL;
   free(buf);
   buf = malloc(512);
   for(i = 0; i != 512 - num; i++){
      buf[i] = 0;
   }
   write(file, buf, 512-num);
   return 0;
}










int printtoc(char* path, int verb, char* find[]){

   FILE* file = fopen(path, "r");
   int num = 1;
   int size = 0;
   int i = 0;
   int b = 1;
   int set = 0;
   char* p;
   char* buf = malloc(5);
   char* header;
   char* body;
   char* fullname = malloc(256);
   char* prefix = malloc(155);

   fseek(file, 257, SEEK_SET);
   fread(buf, 1, 5, file);
   if(f_size(path)%512 != 0 || strncmp(buf, "ustar", 5) != 0){
      fprintf(stderr, "%s: Not an archive\n", path);
      return 0;
   }



   buf = NULL;
   free(buf);
   buf = malloc(100);
   if(find[3] == '\0'){


      if(verb == '0'){ /* VERB = 0 no argv[3] */
      header = malloc(512);
      fseek(file, 0, SEEK_SET);

      while(b == 1){
           prefix = NULL;
           free(prefix);
           prefix = malloc(155);
           size = 0;
           num = fread(buf, 1, 100, file);
           fseek(file, 245, SEEK_CUR);
           num = fread(prefix, 1, 155, file);
           if(num == 0){
           b = 0;}
           if(strlen(prefix) >= 1){
           sprintf(fullname, "%s/%s",prefix, buf);}
           else{
           sprintf(fullname, "%s",buf);
           }
           printf("%s\n",fullname);
           fseek(file, -376, SEEK_CUR);
           buf = NULL;
           free(buf);
           buf = malloc(11);
           num = fread(buf, 1, 11, file);
           fseek(file, 377, SEEK_CUR);
           buf[11]= 0;
           size = strtol(buf, &p, 8);
           size = (size + 512 - 1) / 512;
           fseek(file, 512*size, SEEK_CUR);
           buf = NULL;
           free(buf);
           buf = malloc(100);
           num = fread(buf, 1, 100, file);
           if(buf[0] == 0){
           b = 0;}
           else{
              fseek(file, -100, SEEK_CUR);
              buf = NULL;
              free(buf);
              buf = malloc(100);
           }
        }



   }else{ /* VERB = 1 no argv[3] */
      char* type = malloc(2);
      char* per = malloc(10);
      char* id = malloc(18);
      int length = 0;
      int count = 0;
      char* strtime = malloc(100);
      char* owner = malloc(32);
      char* group = malloc(32);
      char* name = malloc(100);
      char* mode = malloc(8);
      char* ssize = malloc(12);
      char* time = malloc(12);
      header = malloc(512);
      fseek(file, 0, SEEK_SET);
      while(b == 1){
           prefix = NULL;
           free(prefix);
           prefix = malloc(155);
           size = 0;
           num = fread(buf, 1, 100, file); /*100*/
           fseek(file, -100, SEEK_CUR);
           num = fread(name, 1, 100, file);/*100*/
           if(num == 0){
            b = 0;}
           num = fread(mode, 1, 8, file); /*108*/

           fseek(file, 237, SEEK_CUR);
           num = fread(prefix, 1, 155, file);           
           fseek(file, -376, SEEK_CUR); /*124*/
           num = fread(ssize, 1, 12, file); /*136*/
           num = fread(time, 1, 12, file); /*148*/
           fseek(file, 8, SEEK_CUR); /*156*/
           num = fread(type, 1, 1, file);/*157*/
           fseek(file, 108, SEEK_CUR); /*265*/
           num = fread(owner, 1, 32, file);/*329*/
           num = fread(group, 1, 32, file);
           for(i = 0; i!=10; i++)
              per[i] = '-';
           if(type[0] == '2'){
              per[0] = 'l';
           }

	   else if(type[0] == '5'){
              per[0] = 'd';
	   }
        
           if(mode[4] >= '4'){
              per[1] = 'r';
           }

           if(mode[4] == '6' || mode[4] == '7' || mode[4] == '2' || mode[4] == '3'){
              per[2] = 'w';
           }
           if(mode[4] == '1' || mode[4] == '3' || mode[4] == '5' || mode[4] == '7'){
              per[3] = 'x';
           }
           if(mode[5] >= '4'){
              per[4] = 'r';
           }

           if(mode[5] == '6' || mode[5] == '7' || mode[5] == '2' || mode[5] == '3'){
              per[5] = 'w';
           }
           if(mode[5] == '1' || mode[5] == '3' || mode[5] == '5' || mode[5] == '7'){
              per[6] = 'x';
           }  
           if(mode[6] >= '4'){
              per[7] = 'r';
           }

           if(mode[6] == '6' || mode[6] == '7' || mode[6] == '2' || mode[6] == '3'){
              per[8] = 'w';
           }
           if(mode[6] == '1' || mode[6] == '3' || mode[6] == '5' || mode[6] == '7'){
              per[9] = 'x';
           }
           per[10] = 0;
           printf("%s ",per);
           
           sprintf(id, "%s/%s", owner, group);
           count = strlen(id);
           printf("%.17s ",id);
           if(count < 17){
              for(i = 0; i != 17 - count; i++){
                 printf(" ");
              }
           }


           size = strtol(ssize, &p, 8);
           printf("%8d ", size);
           
           time_t mtime = strtol(time, &p, 8);
           struct tm ts;
           ts = *localtime(&mtime);

           strftime(strtime, 100, "%Y-%m-%d %H:%M", &ts);
           printf("%s ", strtime);
           
           if(strlen(prefix) >= 1){
           sprintf(fullname, "%s/%s",prefix, name);}
           else{
           sprintf(fullname, "%s",name);
           }
           printf("%s\n",fullname);



           fseek(file, -205, SEEK_CUR); /*124*/
           buf = NULL;
           free(buf);
           buf = malloc(11);
           num = fread(buf, 1, 11, file);
           fseek(file, 377, SEEK_CUR);
           buf[11]= 0;
           size = strtol(buf, &p, 8);
           size = (size + 512 - 1) / 512;
           fseek(file, (512*size), SEEK_CUR);
           buf = NULL;
           free(buf);
           buf = malloc(100);
           num = fread(buf, 1, 100, file);
           if(buf[0] == 0){
           b = 0;}
           else{
              fseek(file, -100, SEEK_CUR);
              buf = NULL;
              free(buf);
              buf = malloc(100);
           }
        }





   }}
   else{ /* VERB = 0 yes argv[3] */
      if(verb == '0'){
      header = malloc(512);
      fseek(file, 0, SEEK_SET);

      while(b == 1){
           prefix = NULL;
           free(prefix);
           prefix = malloc(155);
           set = 0;
           size = 0;
           num = fread(buf, 1, 100, file);
           if(num == 0){
           b = 0;}
           fseek(file, 245, SEEK_CUR);
           num = fread(prefix, 1, 155, file);
           if(strlen(prefix) >= 1){
           sprintf(fullname, "%s/%s",prefix, buf);}
           else{
           sprintf(fullname, "%s",buf);
           }
           for(i = 3; find[i]!= 0; i++){
           if(strncmp(find[i], fullname, strlen(find[i])) == 0){set = 1;}}
           if(set == 1){
           printf("%s\n",fullname);}

           fseek(file, -376, SEEK_CUR);
           buf = NULL;
           free(buf);
           buf = malloc(11);
           num = fread(buf, 1, 11, file);
           fseek(file, 377, SEEK_CUR);
           buf[11]= 0;
           size = strtol(buf, &p, 8);
           size = (size + 512 - 1) / 512;
           fseek(file, 512*size, SEEK_CUR);
           buf = NULL;
           free(buf);
           buf = malloc(100);
           num = fread(buf, 1, 100, file);
           if(buf[0] == 0){
           b = 0;}
           else{
              fseek(file, -100, SEEK_CUR);
              buf = NULL;
              free(buf);
              buf = malloc(100);
           }
        }





      }
      else{ 
      char* type = malloc(2);
      char* per = malloc(10);
      char* id = malloc(18);
      int length = 0;
      int count = 0;
      char* strtime = malloc(100);
      char* owner = malloc(32);
      char* group = malloc(32);
      char* name = malloc(100);
      char* mode = malloc(8);
      char* ssize = malloc(12);
      char* time = malloc(12);
      header = malloc(512);
      fseek(file, 0, SEEK_SET);

      while(b == 1){
           prefix = NULL;
           free(prefix);
           prefix = malloc(155);
           set = 0;
           size = 0;
           num = fread(buf, 1, 100, file); /*100*/
           fseek(file, -100, SEEK_CUR);
           num = fread(name, 1, 100, file);/*100*/
           if(num == 0){
           b = 0;}
           num = fread(mode, 1, 8, file); /*108*/

           fseek(file, 237, SEEK_CUR);
           num = fread(prefix, 1, 155, file);           
           fseek(file, -376, SEEK_CUR); /*124*/

           num = fread(ssize, 1, 12, file); /*136*/
           num = fread(time, 1, 12, file); /*148*/
           fseek(file, 8, SEEK_CUR); /*156*/
           num = fread(type, 1, 1, file);/*157*/
           fseek(file, 108, SEEK_CUR); /*265*/
           num = fread(owner, 1, 32, file);/*329*/
           num = fread(group, 1, 32, file);
           if(strlen(prefix) >= 1){
           sprintf(fullname, "%s/%s",prefix, name);}
           else{
           sprintf(fullname, "%s",name);
           }
           for(i = 3; find[i]!= 0; i++){
           if(strncmp(find[i], fullname, strlen(find[i])) == 0){set = 1;}}
           if(set == 1){
           for(i = 0; i!=10; i++)
              per[i] = '-';
           if(type[0] == '2'){
              per[0] = 'l';
           }

	   else if(type[0] == '5'){
              per[0] = 'd';
	   }
        
           if(mode[4] >= '4'){
              per[1] = 'r';
           }

           if(mode[4] == '6' || mode[4] == '7' || mode[4] == '2' || mode[4] == '3'){
              per[2] = 'w';
           }
           if(mode[4] == '1' || mode[4] == '3' || mode[4] == '5' || mode[4] == '7'){
              per[3] = 'x';
           }
           if(mode[5] >= '4'){
              per[4] = 'r';
           }

           if(mode[5] == '6' || mode[5] == '7' || mode[5] == '2' || mode[5] == '3'){
              per[5] = 'w';
           }
           if(mode[5] == '1' || mode[5] == '3' || mode[5] == '5' || mode[5] == '7'){
              per[6] = 'x';
           }  
           if(mode[6] >= '4'){
              per[7] = 'r';
           }

           if(mode[6] == '6' || mode[6] == '7' || mode[6] == '2' || mode[6] == '3'){
              per[8] = 'w';
           }
           if(mode[6] == '1' || mode[6] == '3' || mode[6] == '5' || mode[6] == '7'){
              per[9] = 'x';
           }

           per[10] = 0;
           printf("%s ",per);
           
           sprintf(id, "%s/%s", owner, group);
           count = strlen(id);
           printf("%.17s ",id);
           if(count < 17){
              for(i = 0; i != 17 - count; i++){
                 printf(" ");
              }}
           
           size = strtol(ssize, &p, 8);
           printf("%8d ", size);
           
           time_t mtime = strtol(time, &p, 8);
           struct tm ts;
           ts = *localtime(&mtime);

           strftime(strtime, 100, "%Y-%m-%d %H:%M", &ts);
           printf("%s ", strtime);
           

           printf("%s\n",fullname);              
           }


           fseek(file, -205, SEEK_CUR); /*124*/
           buf = NULL;
           free(buf);
           buf = malloc(11);
           num = fread(buf, 1, 11, file);
           fseek(file, 377, SEEK_CUR);
           buf[11]= 0;
           size = strtol(buf, &p, 8);
           size = (size + 512 - 1) / 512;
           fseek(file, (512*size), SEEK_CUR);
           buf = NULL;
           free(buf);
           buf = malloc(100);
           num = fread(buf, 1, 100, file);
           if(buf[0] == 0){
           b = 0;}
           else{
              fseek(file, -100, SEEK_CUR);
              buf = NULL;
              free(buf);
              buf = malloc(100);
           }
        }





      }
   }
   return 0;
}


int eoa(int file){
   int i;
   char* buf;
   buf = malloc(1024);
   for(i = 0; i != 1024; i++){
      buf[i] = 0;
   }
   write(file, buf, 1024);
   return 0;
}





int extract(char* path, int verb, char* find[]){

   FILE* file = fopen(path, "r");
   int num = 1;
   int size = 0;
   int i = 0;
   int b = 1;
   int set = 0;
   char* p;
   char* buf = calloc(100, 1);
   fseek(file, 257, SEEK_SET);
   fread(buf, 1, 5, file);
   if(f_size(path)%512 != 0 || strncmp(buf, "ustar", 5) != 0){
      fprintf(stderr, "%s: Not an archive\n", path);
      return 0;
   }


      while(b == 1){
           struct extract_file * x_file;
           x_file = calloc(1, sizeof(extract_file));           
           size = 0;
           num = fread(x_file->name, 1, 100, file); /*100*/
           fseek(file, -100, SEEK_CUR);
           num = fread(buf, 1, 100, file);/*100*/
           if(num == 0){
            b = 0;}
           num = fread(x_file->mode, 1, 8, file); /*108*/
           fseek(file, 237, SEEK_CUR);
           num = fread(x_file->prefix, 1, 155, file);
           fseek(file, -376, SEEK_CUR); /*124*/
           num = fread(ssize, 1, 12, file); /*136*/
           num = fread(time, 1, 12, file); /*148*/
           fseek(file, 8, SEEK_CUR); /*156*/
           num = fread(type, 1, 1, file);/*157*/
           fseek(file, 108, SEEK_CUR); /*265*/
           num = fread(owner, 1, 32, file);/*329*/
           num = fread(group, 1, 32, file);
           for(i = 0; i!=10; i++)
              per[i] = '-';
           if(type[0] == '2'){
              per[0] = 'l';
           }










   return 0;
}


