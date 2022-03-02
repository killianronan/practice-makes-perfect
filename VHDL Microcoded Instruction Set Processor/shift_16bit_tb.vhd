library IEEE;
use IEEE.std_logic_1164.ALL;

entity shift_16bit_tb is
end shift_16bit_tb;

architecture Behavioral of shift_16bit_tb is
    component shift_16bit is
        Port ( 
		    bits : in std_logic_vector (15 downto 0);
			FS : in std_logic_vector (4 downto 0);
			lR : in std_logic;
			lL : in std_logic;
			H : out std_logic_vector (15 downto 0)
		);
    end component;
    --inputs    
    signal bits : std_logic_vector(15 downto 0) := x"0000";
    signal FS : std_logic_vector(4 downto 0) := "00000";
    signal Lr : std_logic := '0';
    signal Ll : std_logic := '0';
    --outputs 
    signal H : std_logic_vector(15 downto 0) := x"0000";  
begin
    UUT: shift_16bit
    Port Map(
        bits => bits,
        FS => FS,
        lR => lR,
        lL => lL,
        H => H
    );
sim_proc :process
begin  
        --Assign H
       	bits <= x"0F00"; 
        FS <= "10000";
        wait for 2ns;
        --Shift Left
        FS <= "11000";
        wait for 2ns;
        --Shift Right
       	FS <= "10100";
        wait for 2ns;
       	FS <= "01100";--should not shift do nothing
        wait for 2ns;
     end process;
end Behavioral;
