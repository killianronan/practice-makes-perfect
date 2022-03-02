----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 20:29:04
-- Design Name: 
-- Module Name: CPU - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity CPU is
    Port (
        Reset,Clk : in std_logic;
        reg0: out std_logic_vector(15 downto 0);
        reg1: out std_logic_vector(15 downto 0);
        reg2: out std_logic_vector(15 downto 0);
        reg3: out std_logic_vector(15 downto 0);
        reg4: out std_logic_vector(15 downto 0);
        reg5: out std_logic_vector(15 downto 0);
        reg6: out std_logic_vector(15 downto 0);
        reg7: out std_logic_vector(15 downto 0)
    );end CPU;

architecture Behavioral of CPU is
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
component mp_control is 
    Port ( 
           data_in : in  STD_LOGIC_VECTOR(15 downto 0);
           Clk,reset, v, c, n, z: in STD_LOGIC;
           PC_out : out  STD_LOGIC_VECTOR(15 downto 0);
           FS : out STD_LOGIC_VECTOR(4 downto 0);
           DR,SA,SB, MS : out  STD_LOGIC_VECTOR(2 downto 0);
           TD,TA,TB,MB,RW,MD,MM,MW,PL,PI,IL,MC: out STD_LOGIC;
           NA : out std_logic_vector(7 downto 0)
           );
end component;
component memory_M is 
        Port ( 
            addr_in,data : in std_logic_vector(15 downto 0);
            Clk,MW : in std_logic;
            result : out std_logic_vector(15 downto 0)
        );
end component;
    signal v,c,n,z,TD, TA, TB,MB,MM,MW,MD,RW : std_logic;   
    signal DR, SA, SB : std_logic_vector(2 downto 0);    
    signal FS : std_logic_vector(4 downto 0);
    signal memory, pc_out, data, PC ,out_address: std_logic_vector(15 downto 0);
begin

    MC : MP_control Port Map(
            v => v,
            c => c,
            n => n,
            z => z,
            data_in => memory,
            clk => clk,
            reset => reset,
            
            PC_out => pc_Out,
            
            MW => MW,
            MM => MM,
            MD => MD,
            MB => MB,
            
            FS => FS,
            RW => RW,
            
            TD => TD,
            TA => TA,
            TB => TB,
            
            DR => DR,
            SA => SA,
            SB => SB
        );
    mem_M : memory_M Port Map(
            addr_in => out_address,
            data => data,
            clk => clk,
            MW => MW,
            result => memory
        );

    DP : datapath Port Map(
            SB => SB,
            a_address(2 downto 0) => SA,
            a_address(3) => TA,
            b_address(2 downto 0) => SB,
            b_address(3) => TB,
            d_address(2 downto 0) => DR,
            d_address(3) => TD,
            data => memory,
            PC => pc_Out,
            muxB_select => MB,
            muxD_select => MD,
            muxM_select => MM,
                        
            v => v,
            c => c,
            n => n,
            z => z,
            FS => FS,
            RW => RW,
            Clk => clk,
            address => out_address,
            data_out => data,
            
            reg0_out => reg0,
            reg1_out => reg1,
            reg2_out => reg2,
            reg3_out => reg3,
            reg4_out => reg4,
            reg5_out => reg5,
            reg6_out => reg6,
            reg7_out => reg7
        );
        
end Behavioral;
