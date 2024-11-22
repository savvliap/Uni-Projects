///////////////priority reg///////////////////////////
module priority_reg(
 input logic clk,
 input logic rst,
 input logic [2:0]d_i,      
 input logic en_i,           
 output logic [2:0]q_o
);

always_ff @(posedge clk) begin  //kanw thn eksodou q_o gt einai aplo nomizw 
   if (~rst) begin
      q_o=3'b111;
   end else if (en_i) begin    //prepei na proseksw oti xreiazetai kai to sima en
      q_o<=d_i;
	end
end

endmodule 

//////////////////arbiter////////////////////////

module arbiter (
 input logic[7:0] reqs_i,      // The requests to choose from
 input logic[2:0] lowp_i,
 output logic[2:0] grants_o,   // The winner den einai one hot
 output logic[3:0] cnt_o,      // upologizei to plithos ton requests 
 output logic any_grant_o      // Signals that there is at least one winner
);

always_comb begin                   //kanw to any grant 
   if (~|reqs_i) begin  //lew oti an den eisagw 0 requests tote to 
      any_grant_o=1'b1;        //anygrant tha einai 1 thelei kati allo?
   end else begin
      any_grant_o=1'b0;
	end
end

always_comb begin                   //cnt_o
   logic [3:0] temp;
   temp=3'b000;               //kanw elegxw kathe bit tis eisodou gia to an einai 1 kai an einai tote anevazw mia metavliti temp kai tin ekxwrw epeita sthn cnt_o.
   for(int i=0;i<8;i++) begin 
      if(reqs_i[i]==1) begin
         temp=temp +1;
      end 
		end
    cnt_o=temp;
end

logic [2:0] pri;        //dimiourgw priority
logic [2:0] counter;    //dimiourgw ena counter
always_comb begin 

   if (lowp_i==3'b111) begin     //sunthiki an to priority einai 7 n ginei 0
      pri=3'b000;
   end else begin 
      pri=lowp_i+1; 
   end                           //alliws na sunexisei 
   

   for (int i=0; i<8;i++) begin             //to counter na metraei mexri to 7=reqs
      if (reqs_i[pri]==1'b1) begin    //an exw 1 stop                     
         break;
      end else begin
         counter++;  
      end         
      if(pri==3'b111) begin         //sunthiki gia prior
         pri=3'b000; 
      end else begin
         pri++;      
      end
   end
    grants_o=pri;
end
endmodule  

////////////////q_o_decoder/////////////////////////

module q_o_decoder(
input logic [2:0] sig_i,
output logic [6:0] sig_o);

always_comb begin
   case(sig_i)
      3'b000: sig_o=~7'b0111111;
      3'b001: sig_o=~7'b0000110;
      3'b010: sig_o=~7'b1011011;
      3'b011: sig_o=~7'b1001111;
      3'b100: sig_o=~7'b1100110;
      3'b101: sig_o=~7'b1101101;
      3'b110: sig_o=~7'b1111101;
      3'b111: sig_o=~7'b0000111;
   endcase 
end
endmodule
/////////////////cnt_o_decoder////////////////////////

module cnt_o_decoder(
input logic [3:0] sig_i,
output logic [6:0] sig_o);

always_comb begin 
   case(sig_i)
      4'b0000: sig_o=~7'b0111111;
      4'b0001: sig_o=~7'b0000110;
      4'b0010: sig_o=~7'b1011011;
      4'b0011: sig_o=~7'b1001111;
      4'b0100: sig_o=~7'b1100110;
      4'b0101: sig_o=~7'b1101101;
      4'b0110: sig_o=~7'b1111101;
      4'b0111: sig_o=~7'b0000111;
      4'b1000: sig_o=~7'b1111111;
      4'b1001: sig_o=~7'b1101111;
      4'b1010: sig_o=~7'b1110111;
      4'b1011: sig_o=~7'b1111100;
      4'b1100: sig_o=~7'b0111001;
      4'b1101: sig_o=~7'b1011110;
      4'b1110: sig_o=~7'b1111001;
      4'b1111: sig_o=~7'b1110001;
   endcase 
end

endmodule
///////////////grants_o_decoder/////////////////////////

module grants_o_decoder(
input logic enable,
input logic [2:0] sig_i,
output logic [7:0] sig_o);


always_comb begin
   if (enable) begin 
      case(sig_i)
         3'b000: sig_o=8'b00000001;
         3'b001: sig_o=8'b00000010;
         3'b010: sig_o=8'b00000100;
         3'b011: sig_o=8'b00001000;
         3'b100: sig_o=8'b00010000;
         3'b101: sig_o=8'b00100000;
         3'b110: sig_o=8'b01000000;
         3'b111: sig_o=8'b10000000;
      endcase 
   end else begin 
      sig_o=8'b00000000;
   end
end

endmodule
///////////////rr_arbiter_buggy/////////////////////////
 
 module rr_arbiter_buggy(

input logic rst_i,
input logic clk,
input logic [7:0]reqs_i,
output logic [6:0]sig_q_o,
output logic [6:0]sig_cnt_o,
output logic [7:0]sig_grants_out);

logic [2:0]wire_q_o;
logic wire_any_grant_o;
logic [2:0]wire_grants_o;
logic [3:0]wire_cnt_o;

priority_reg r( 
.clk(clk), 
.rst(rst_i), 
.d_i(wire_grants_o), 
.en_i(wire_any_grant_o),
.q_o(wire_q_o));

arbiter a(
.reqs_i(reqs_i),
.lowp_i(wire_q_o),
.grants_o(wire_grants_o),
.cnt_o(wire_cnt_o),
.any_grant_o(wire_any_grant_o)
);

q_o_decoder b(
.sig_i (wire_q_o),
.sig_o(sig_q_o));

cnt_o_decoder c(
.sig_i(wire_cnt_o), 
.sig_o(sig_cnt_o));

grants_o_decoder d(
.enable(wire_any_grant_o),
.sig_i(wire_grants_o),
.sig_o(sig_grants_out));

endmodule 