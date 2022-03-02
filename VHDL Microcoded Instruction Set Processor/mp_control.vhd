library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity mp_control is
    Port ( 
           data_in : in  STD_LOGIC_VECTOR(15 downto 0);
           Clk,reset, v, c, n, z: in STD_LOGIC;
           PC_out : out  STD_LOGIC_VECTOR(15 downto 0);
           FS : out STD_LOGIC_VECTOR(4 downto 0);
           DR,SA,SB, MS : out  STD_LOGIC_VECTOR(2 downto 0);
           TD,TA,TB,MB,RW,MD,MM,MW,PL,PI,IL,MC: out STD_LOGIC;
           NA : out std_logic_vector(7 downto 0)
           );
end mp_control;

architecture Behavioral of mp_control is
   component sign_extend
  Port ( SB : in  STD_LOGIC_VECTOR(2 downto 0);
           DR : in  STD_LOGIC_VECTOR(2 downto 0);
           result : out  STD_LOGIC_VECTOR(15 downto 0));
    end component;
   
    component control_address_register
        Port ( 
           S,RESET ,Clk: in std_logic;
           op_in : in std_logic_vector(7 downto 0);
           CAR_out : out std_logic_vector(7 downto 0)
         );
    end component;
    component instruction_register
      Port (
           instruction : in std_logic_vector(15 downto 0);
           IL : in std_logic;
           Clk : in std_logic;
           DR,SA,SB : out std_logic_vector(2 downto 0);
           op_out : out std_logic_vector(6 downto 0)
       );
       
    end component;
    component program_counter
        Port ( 
          data_in : in std_logic_vector(15 downto 0);
          PL, PI, reset,Clk: in std_logic;
          PC_result : out std_logic_vector(15 downto 0)
         );
    end component;       
      
    component mux2_8bit
    Port ( s : in  STD_LOGIC;
           in1 : in  STD_LOGIC_VECTOR (7 downto 0);
           in2 : in  STD_LOGIC_VECTOR (7 downto 0);
           z : out  STD_LOGIC_VECTOR (7 downto 0));
    end component;
    component mux8_1bit
    Port ( s : in  STD_LOGIC_VECTOR(2 downto 0);
           in1 : in  STD_LOGIC;
           in2 : in  STD_LOGIC;
           in3 : in  STD_LOGIC;
           in4 : in  STD_LOGIC;
           in5 : in  STD_LOGIC;
           in6 : in  STD_LOGIC;    
           in7 : in  STD_LOGIC;
           in8 : in  STD_LOGIC;                             
           z : out  STD_LOGIC);
    end component;
    component control_memory
        Port ( 
           CAR_in : in std_logic_vector(7 downto 0);
           NA : out  std_logic_vector(7 downto 0);
           MS : out  std_logic_vector(2 downto 0);
           FS : out std_logic_vector(4 downto 0);
           MC,IL,PI,PL,TD,TA,TB,MB,MD,RW,MM,MW : out std_logic
        );
    end component;
 
    --Internal signals
    signal NA_sig : std_logic_vector(7 downto 0);
    signal MS_sig : std_logic_vector(2 downto 0);
    signal PL_sig, PI_sig, IL_sig, MC_sig : std_logic;
    
    signal extended_in : std_logic_vector(15 downto 0);
    signal S_out : std_logic;
    signal C_out : std_logic_vector(7 downto 0);

    signal Ext_SA : std_logic_vector(2 downto 0);
    signal Ext_SB : std_logic_vector(2 downto 0);
    signal Ext_DR : std_logic_vector(2 downto 0); 
    signal op : std_logic_vector(6 downto 0);
    signal CAR_in : std_logic_vector(7 downto 0);
    signal not_Z : std_logic;
    signal not_C : std_logic; 
begin
    s_ext: sign_extend
        port map(
            SB => Ext_SB,
            DR => Ext_DR,
            result => extended_in
        ); 
    pc: program_counter
        port map(
          data_in =>  extended_in,
          PL => PL_sig,
          PI => PI_sig,
          Clk => clk,
          reset => reset,
          PC_result => PC_out
        ); 
    ireg: instruction_register
        port map (
            instruction => data_in,
            IL => IL_sig,
            Clk => clk,
            DR => Ext_DR,
            SA => Ext_SA,
            SB => Ext_SB,
            op_out => op
        );  
    CAR: control_address_register
        port map (
            op_in => C_out,
            S => S_out,
            Clk => Clk,
            reset => reset,
            CAR_out => CAR_in
        );     
    muxC: mux2_8bit
        port map (
            s => MC_sig,
            in1 => NA_sig,
            in2(0) => op(0),
            in2(1) => op(1),
            in2(2) => op(2),
            in2(3) => op(3),
            in2(4) => op(4),
            in2(5) => op(5),
            in2(6) => op(6),
            in2(7) => '0',
            z => C_OUT
        );
      muxS: mux8_1bit
        port map(
            s => MS_sig,
            In1 => '0',
            In2 => '1',
            In3 => c,
            In4 => v,
            In5 => z,
            In6 => n,
            In7 => not_C,
            In8 => not_Z,
            z => S_out
        );             
    control_mem: control_memory
        port map (
            CAR_in => CAR_in,
            NA => NA_sig,
            MS => MS_sig,
            FS => FS,
            MC => MC_sig,
            IL => IL_sig,
            PI => PI_sig,
            PL => PL_sig,
            TD => TD,
            TA => TA,
            TB => TB,
            MB => MB,
            MD => MD,
            RW => RW,
            MM => MM,
            MW => MW
        );
    DR <= Ext_DR;
    SA <= Ext_SA;
    SB <= Ext_SB;
 
    NA <= NA_sig;
    MS <= MS_sig;
    PI <= PI_sig;
    IL <= IL_sig;
    MC <= MC_sig;
    PL <= PL_sig;


end Behavioral;
