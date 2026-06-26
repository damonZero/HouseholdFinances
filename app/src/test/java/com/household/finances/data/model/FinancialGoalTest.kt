package com.household.finances.data.model

import org.junit.Assert.*
import org.junit.Test

class FinancialGoalTest {

    @Test
    fun `FinancialGoal creation with all fields`() {
        val goal = FinancialGoal(
            id = 1,
            name = "买房首付",
            type = GoalType.SAVINGS,
            targetAmount = 500000.0,
            currentAmount = 200000.0,
            deadline = System.currentTimeMillis(),
            description = "3年内攒够首付",
            status = GoalStatus.ACTIVE
        )

        assertEquals(1L, goal.id)
        assertEquals("买房首付", goal.name)
        assertEquals(GoalType.SAVINGS, goal.type)
        assertEquals(500000.0, goal.targetAmount, 0.001)
        assertEquals(200000.0, goal.currentAmount, 0.001)
        assertEquals(GoalStatus.ACTIVE, goal.status)
    }

    @Test
    fun `FinancialGoal creation with default values`() {
        val goal = FinancialGoal(
            name = "测试目标",
            type = GoalType.INVESTMENT,
            targetAmount = 100000.0
        )

        assertEquals(0L, goal.id)
        assertEquals("测试目标", goal.name)
        assertEquals(GoalType.INVESTMENT, goal.type)
        assertEquals(100000.0, goal.targetAmount, 0.001)
        assertEquals(0.0, goal.currentAmount, 0.001)
        assertEquals(GoalStatus.ACTIVE, goal.status)
    }

    @Test
    fun `GoalType enum values`() {
        assertEquals(4, GoalType.entries.size)
        assertNotNull(GoalType.SAVINGS)
        assertNotNull(GoalType.INVESTMENT)
        assertNotNull(GoalType.DEBT_PAYOFF)
        assertNotNull(GoalType.RETIREMENT)
    }

    @Test
    fun `GoalStatus enum values`() {
        assertEquals(4, GoalStatus.entries.size)
        assertNotNull(GoalStatus.ACTIVE)
        assertNotNull(GoalStatus.COMPLETED)
        assertNotNull(GoalStatus.PAUSED)
        assertNotNull(GoalStatus.CANCELLED)
    }

    @Test
    fun `FinancialGoal progress calculation`() {
        val goal = FinancialGoal(
            name = "测试",
            type = GoalType.SAVINGS,
            targetAmount = 500000.0,
            currentAmount = 200000.0
        )

        val progress = goal.currentAmount / goal.targetAmount
        assertEquals(0.4, progress, 0.001)
    }

    @Test
    fun `FinancialGoal completion check`() {
        val goal = FinancialGoal(
            name = "测试",
            type = GoalType.SAVINGS,
            targetAmount = 100000.0,
            currentAmount = 100000.0,
            status = GoalStatus.COMPLETED
        )

        assertTrue(goal.currentAmount >= goal.targetAmount)
        assertEquals(GoalStatus.COMPLETED, goal.status)
    }

    @Test
    fun `FinancialGoal copy with updated amount`() {
        val original = FinancialGoal(
            id = 1,
            name = "测试",
            type = GoalType.SAVINGS,
            targetAmount = 100000.0,
            currentAmount = 50000.0
        )
        val updated = original.copy(currentAmount = 75000.0)

        assertEquals(1L, updated.id)
        assertEquals("测试", updated.name)
        assertEquals(75000.0, updated.currentAmount, 0.001)
    }
}
