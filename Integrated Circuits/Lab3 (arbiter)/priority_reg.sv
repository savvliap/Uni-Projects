module priority_reg(
 input logic clk,
 input logic rst,
 input logic [2:0]d_i,      //εισάγει τους regs που δεν έχουν προτεραιότητα 
 input logic en_i,          //οταν ειναι εν=1 αποθηκέυει 
 output logic [2:0]q_o
);

always_ff @(posedge clk)   //kanw thn eksodou q_o gt einai aplo nomizw 
begin 
   if (rst) 
      q_o=3'b111;
   else if (en_i)          //prepei na proseksw oti xreiazetai kai to sima en
      q_o<=d_i;
end

endmodule 