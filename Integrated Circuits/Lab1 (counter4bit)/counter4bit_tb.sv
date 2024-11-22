module counter4bit_tb;

logic clk;
logic reset;
logic satEn;
logic [3:0]counterout;

//clock
always begin  
 clk= 1;
 #5ns;
 clk= 0;
 #5ns;
end

counter4bit
 countername(clk, reset, satEn, counterout);
 initial begin
 $display("starting testbench"); //start 
 //assigning values for satEn reset and reset 
 satEn<= 0;                       
 reset<= 0;
 
 @(posedge clk);
 reset<= 1;
 
 @(posedge clk);
 reset<= 0;                      
 satEn<= 1;
 //when satEn=1 the counter counts till 11.
 //for the two last repeats the counter will start fromm 00
 repeat(13)begin  
 
 @(posedge clk);                 
  $strobe("@%0t:counterout->%b", $time, counterout); //dixnei eksodous 
 end
 satEn<= 0;    
//SatEn=0 trial 
//after the 16 repetition the counter will reset from 0.
 repeat (17) begin 
  @(posedge clk);
  $strobe("@%0t:counterout->%b", $time, counterout);
 end
 
 //reset 
 repeat(2)
  @(posedge clk);
  reset<= 1;
  @(posedge clk);
  reset <= 1;
  repeat(3) @(posedge clk);
  $display("Finished");
 end

endmodule