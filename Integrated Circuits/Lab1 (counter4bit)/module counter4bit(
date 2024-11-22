module counter4bit(
input logic clk,
input logic reset,
input logic satEn,
output logic [3:0] countout);

always_ff @( posedge clk, posedge reset, posedge satEn) 
begin
 if (reset) countout<=0;                //an exw reset thelw i eksodos na einai 0 
 else begin              
  if (satEn)                            //an satEn=1 thelw na 
   if (countout== 4'b1011) countout<=0; //elegxei an h eksodos einai 11 
   else countout<=countout+1;           //an den einai na thn auksanei kata 1
  else begin                             
   if (countout==4'b1111) countout<=0;  //an satEn=0 thelw na elegxei an einai 15
    else countout<=countout +1;         //an den einai na sunexizei thn metrisi 
   end
  end

end
endmodule