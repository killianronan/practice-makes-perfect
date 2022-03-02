library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity datapath is
	Port (
		data : in std_logic_vector(15 downto 0);
		PC : in std_logic_vector(15 downto 0);
		FS : in std_logic_vector(4 downto 0);
		SB : in std_logic_vector(2 downto 0);

	    a_address : in std_logic_vector(3 downto 0);
		b_address : in std_logic_vector(3 downto 0);
		d_address : in std_logic_vector(3 downto 0);
		muxB_select : in std_logic;
		muxD_select : in std_logic;
		muxM_select : in std_logic;
		RW : in std_logic;
		Clk : in std_logic;
	    v : out std_logic;
		c : out std_logic;
		n : out std_logic;
		z : out std_logic;
		
		address : out std_logic_vector(15 downto 0);
	    data_out : out std_logic_vector(15 downto 0);
		reg0_out : out std_logic_vector(15 downto 0);
		reg1_out : out std_logic_vector(15 downto 0);
		reg2_out : out std_logic_vector(15 downto 0);
		reg3_out : out std_logic_vector(15 downto 0);
		reg4_out : out std_logic_vector(15 downto 0);
		reg5_out : out std_logic_vector(15 downto 0);
		reg6_out : out std_logic_vector(15 downto 0);
		reg7_out : out std_logic_vector(15 downto 0);
		reg8_out : out std_logic_vector(15 downto 0)
	);
end datapath;

architecture Behavioral of datapath is
	component mux2_16bit
 Port ( s : in  STD_LOGIC;
           in1 : in  STD_LOGIC_VECTOR (15 downto 0);
           in2 : in  STD_LOGIC_VECTOR (15 downto 0);
           z : out  STD_LOGIC_VECTOR (15 downto 0));
	end component;
	component function_unit
	Port ( 
		A : in std_logic_vector(15 downto 0);
		B : in std_logic_vector(15 downto 0);
		FS : in std_logic_vector(4 downto 0);
		V : out std_logic;
		C : out std_logic;
		N : out std_logic;
		Z : out std_logic;	
		F : out std_logic_vector(15 downto 0)
	);
	end component;
	component regfile
	Port(
    data_in : in std_logic_vector(15 downto 0);
    RW : in std_logic;
    DA: in std_logic_vector(3 downto 0);	
    AA : in std_logic_vector(3 downto 0);
    BA : in std_logic_vector(3 downto 0);
    Clk : in std_logic;
    a_out : out std_logic_vector(15 downto 0);
    b_out : out std_logic_vector(15 downto 0);
    reg0: out std_logic_vector(15 downto 0);
    reg1: out std_logic_vector(15 downto 0);
    reg2: out std_logic_vector(15 downto 0);
    reg3: out std_logic_vector(15 downto 0);
    reg4: out std_logic_vector(15 downto 0);
    reg5: out std_logic_vector(15 downto 0);
    reg6: out std_logic_vector(15 downto 0);
    reg7: out std_logic_vector(15 downto 0);
    reg8: out std_logic_vector(15 downto 0)
    );
	end component;
	component Zero_fill
        Port ( 
            SB : in std_logic_vector(2 downto 0);
            z_out : out std_logic_vector(15 downto 0)
        );
        end component;

	signal Bus_A, Bus_B, B, in_data, fun, z_in: std_logic_vector(15 downto 0);
		
	begin
	reg: regfile Port Map(
		AA => a_address,	
		BA => b_address,
		DA => d_address,
		data_in => in_data,
		a_out => Bus_A,
		b_out => B,
		RW => RW, 
		Clk => Clk,
		reg0 => reg0_out,
        reg1 => reg1_out,
        reg2 => reg2_out,
        reg3 => reg3_out,
        reg4 => reg4_out,
        reg5 => reg5_out,
        reg6 => reg6_out,
        reg7 => reg7_out,
        reg8 => reg8_out
	);
    zero: Zero_fill Port Map(
         SB => SB,
         z_out => z_in
    ); 
	
	muxB: mux2_16bit Port Map(
		In1 => B,
		In2 => z_in,
		s => MuxB_Select,
		Z => Bus_B
	);
	
	fun_unit: function_unit Port Map(
		A => Bus_A,
		B => Bus_B,
		FS => FS,
		V => v,
		C => c,
		N => n,
		Z => z,	
		F => fun
	);
	
	muxD: mux2_16bit Port Map(
		In1 => fun,
		In2 => data,
		s => muxD_select,
		Z => in_data
	);
	muxM: mux2_16bit Port Map(
		In1 => Bus_A,
		In2 => PC,
		s => muxM_select,
		Z => address
	);
	data_out <= Bus_B;
end Behavioral;
