const { useInput } = require("ink");
const React = require("react");
const { useReducer } = require("react");
const { initialState, reducer } = require("./reducer");
const WizardContext = require("./WizardContext");

const WizardProvider = ({ children }) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	useInput((input, key) => {
		if (input === "]") dispatch({ type: "NEXT_STEP" });
		if (input === "[") dispatch({ type: "PREV_STEP" });
	});

	const providerValue = {
		...state,
	};
	return (
		<WizardContext.Provider value={providerValue}>
			{children}
		</WizardContext.Provider>
	);
};

module.exports = WizardProvider;
