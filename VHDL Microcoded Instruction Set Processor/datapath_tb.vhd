library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity datapath_tb is
end datapath_tb;
architecture Behavioral of datapath_tb is
    component datapath is
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
    end component;
    --inputs 
    signal data, PC : std_logic_vector(15 downto 0) := x"0000";
    signal a_address, d_address, b_address : std_logic_vector(3 downto 0) := "0000";
    signal MuxB_select, MuxD_select, MuxM_select, RW, Clk : std_logic := '0';
    signal SB : std_logic_vector(2 downto 0) := "000";
    signal FS : std_logic_vector(4 downto 0) := "00000";
    --outputs
    signal v, c, n, z : std_logic := '0';
    signal address, data_out, reg0_out, reg1_out, reg2_out, reg3_out, reg4_out, reg5_out, reg6_out, reg7_out, reg8_out : std_logic_vector(15 downto 0) := x"0000";

begin
    UUT: datapath
    Port Map(
        data => data,
		PC => PC,
		SB => SB,
		a_address => a_address,
		b_address => b_address,
		d_address => d_address,
		FS => FS,
		MuxM_select => MuxM_select,
		MuxB_select => MuxB_select,
		MuxD_select => MuxD_select,
		RW => RW,
		Clk => Clk,
		v => v,
		c => c,
		n => n,
		z => z,
		reg0_out => reg0_out,
		reg1_out => reg1_out,
		reg2_out => reg2_out,
		reg3_out => reg3_out,
		reg4_out => reg4_out,
		reg5_out => reg5_out,
		reg6_out => reg6_out,
		reg7_out => reg7_out,	
		reg8_out => reg8_out,	
		data_out => data_out,
		address => address
    );
    
sim_proc :process
begin
        RW <= '1';
        MuxM_select <= '0';
        PC <= x"0000";
        MuxD_select <= '1';
        MuxB_select <= '0';
        
        --reg1
        data <= x"0001";
        d_address <= "0000";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;

        --reg2
        data <= x"0002";
        d_address <= "0001";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;
 
        --reg3
        data <= x"0003";
        d_address <= "0010";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;
        
        --reg4
        data <= x"0004";
        d_address <= "0011";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;

        --reg5
        data <= x"0005";
        d_address <= "0100";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;
        
        --reg6
        data <= x"0006";
        d_address <= "0101";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;
 
        --reg7
        data <= x"0007";
        d_address <= "0110";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;
        
        --reg8
        data <= x"0008";
        d_address <= "0111";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;
        
        --reg9
        data <= x"0009";
        d_address <= "1000";
        Clk <= '1';
        wait for 20ns;
        Clk <= '0';
        wait for 20ns;

        --select reg2 = 0x0002
        a_address <= "0010";
        wait for 25ns;
        
        --select reg4 = 0x004
        b_address <= "0100";
        wait for 25ns; 
        
        --Test PC Load through
        RW <= '0';
        MuxM_select <= '1';
        MuxD_select <= '1';
        MuxB_select<= '0';
        
        PC <= x"99FF";
        wait for 25ns; 
        
        --Test MuxB
        SB <= "010";
        MuxB_select <= '1';
        wait for 25ns;
            
          
        wait;
     end process;
end Behavioral;
