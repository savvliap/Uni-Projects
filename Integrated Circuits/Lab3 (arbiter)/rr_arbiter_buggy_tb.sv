module rr_arbiter_buggy_tb;
parameter REQS = 4;
parameter BUG  = 1;

logic clk;
logic rst;
logic[REQS-1:0] reqs_i;
logic[REQS-1:0] grants_o;
logic any_grant_o;

always begin 
 clk = 1;
   #5ns; 
 clk = 0;
   #5ns;
end

rr_arbiter_buggy
 #(.REQS(REQS), .BUG(BUG))
rr_arbiter_buggy_name
 (.clk (clk),
  .rst (rst),
  .reqs_i (reqs_i),
  .grants_o (grants_o),
  .any_grant_o (any_grant_o));

initial begin
$display("Starting Testbench");
rst <= 1;

@(posedge clk);
rst<=0;
reqs_i <= 4'b0000;

repeat(3) @(posedge clk);
$strobe("@%0t: grants_o-> %b", $time, grants_o); 

repeat(3) @(posedge clk);
rst<=1;

@(posedge clk);
rst<=0;
reqs_i <= 4'b1010;

repeat(5) @(posedge clk);
$strobe("@%0t: grants_o-> %b", $time, grants_o);

repeat(3) @(posedge clk);
rst<=1;

@(posedge clk);
rst<=0;
reqs_i <= 4'b1111;

repeat(5) @(posedge clk);
$strobe("@%0t: grants_o-> %b", $time, grants_o);

end
endmodule