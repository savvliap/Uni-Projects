module tictactoe (
input logic [8:0] x,
input logic [8:0] o,
output logic error,
output logic full,
output logic winX,
output logic winO,
output logic noWin);

always_comb
 begin
   if ((x & o) != 9'b000000000)begin 
	error=1;
	end
   else begin 
	error=0;
   end
end 


always_comb
  begin
    if((x^o)==9'b111111111) begin 
	 full=1;
	 end
    else begin
	 full=0;
    end
 end

always_comb
 begin 
  if (~error &&(x[4]*(x[3]*x[5] || x[0]*x[8] || x[1]*x[7]) || x[0]*(x[1]*x[2]) || x[8]*(x[6]*x[7] || x[2]*x[5])))begin 
  winX=1;
  end
  else begin 
  winX=0;
  end
 end


always_comb
  begin 
   if (~error &&(o[4]*(o[3]*o[5] || o[0]*o[8] || o[1]*o[7]) || o[0]*(o[1]*o[2]) || o[8]*(o[6]*o[7] || o[2]*o[5]))) begin 
	winO=1;
	end
   else begin 
	winO=0;
  end
 end

assign noWin = (~winX && ~winO);

endmodule