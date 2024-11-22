module tic_tac_top (
  input  logic[4:0] x_i,
  input  logic[4:0] o_i,
  output logic      error_o,
  output logic      full_o,
  output logic      winx_o,
  output logic      wino_o,
  output logic      nowin_o
);
  
logic[8:0] x_int;
logic[8:0] o_int;

assign x_int = {4'b1000, x_i} ;
assign o_int = {4'b0111, o_i};

tictactoe ttt (
  .x     (x_int),
  .o     (o_int),
  .error (error_o),
  .full  (full_o),
  .winX  (winx_o),
  .winO  (wino_o),
  .noWin (nowin_o)
);
  
endmodule
